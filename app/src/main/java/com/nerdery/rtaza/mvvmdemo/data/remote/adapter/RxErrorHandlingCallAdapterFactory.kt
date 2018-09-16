package com.nerdery.rtaza.mvvmdemo.data.remote.adapter

import com.nerdery.rtaza.mvvmdemo.data.core.Error
import io.reactivex.*
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import timber.log.Timber
import java.io.IOException
import java.lang.reflect.Type
import java.net.HttpURLConnection

/**
 * Retrofit CallAdapter.Factory for centrally adapting all Retrofit exceptions to custom exceptions that contain
 * app-specific error codes and messages.
 */
class RxErrorHandlingCallAdapterFactory(scheduler: Scheduler) : CallAdapter.Factory() {
    private val rxJava2CallAdapterFactory by lazy {
        RxJava2CallAdapterFactory.createWithScheduler(scheduler)
    }

    override fun get(returnType: Type, annotations: Array<Annotation>, retrofit: Retrofit): CallAdapter<*, *>? {
        return RxCallAdapterWrapper(
            rxJava2CallAdapterFactory.get(returnType, annotations, retrofit) as CallAdapter<out Any, *>
        )
    }

    private class RxCallAdapterWrapper<R>(private val rxJava2CallAdapter: CallAdapter<R, *>) : CallAdapter<R, Any> {

        override fun responseType(): Type {
            return rxJava2CallAdapter.responseType()
        }

        override fun adapt(call: Call<R>): Any {
            val stream = rxJava2CallAdapter.adapt(call)

            return when (stream) {
                is Flowable<*> -> stream.onErrorResumeNext { throwable: Throwable ->
                    Flowable.error(adaptException(throwable))
                }
                is Single<*> -> stream.onErrorResumeNext { throwable ->
                    Single.error(adaptException(throwable))
                }
                is Maybe<*> -> stream.onErrorResumeNext { throwable: Throwable ->
                    Maybe.error(adaptException(throwable))
                }
                is Completable -> stream.onErrorResumeNext { throwable: Throwable ->
                    Completable.error(adaptException(throwable))
                }
                is Observable<*> -> stream.onErrorResumeNext { throwable: Throwable ->
                    Observable.error(adaptException(throwable))
                }
                else -> {
                    Timber.wtf("Unknown RxJava stream type")
                    stream
                }
            }
        }

        private fun adaptException(throwable: Throwable): Error {
            Timber.e(throwable)

            return when (throwable) {
                is IOException ->
                    // Network error (no network connection, timeout)
                    Error.HttpClientIo(throwable)
                is HttpException ->
                    // HTTP error (server down, unauthorized request)
                    when (throwable.code()) {
                        HttpURLConnection.HTTP_INTERNAL_ERROR -> Error.HttpServer(throwable)
                        HttpURLConnection.HTTP_UNAUTHORIZED -> Error.HttpUnauthorized(throwable)
                        else -> Error.Unknown(throwable)
                    }
                else ->
                    // Unknown error
                    Error.Unknown(throwable)
            }
        }
    }
}