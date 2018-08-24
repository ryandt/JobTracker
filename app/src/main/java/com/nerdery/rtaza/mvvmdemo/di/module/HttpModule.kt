package com.nerdery.rtaza.mvvmdemo.di.module

import com.nerdery.rtaza.mvvmdemo.BuildConfig
import com.nerdery.rtaza.mvvmdemo.data.remote.HttpConstants
import com.nerdery.rtaza.mvvmdemo.data.remote.adapter.RxErrorHandlingCallAdapterFactory
import com.nerdery.rtaza.mvvmdemo.data.remote.api.JobWebApi
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import timber.log.Timber

@Module
class HttpModule {

    @Provides
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    @Provides
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(HttpConstants.TIMEOUT_IO, HttpConstants.TIMEOUT_TIME_UNIT)
            .readTimeout(HttpConstants.TIMEOUT_IO, HttpConstants.TIMEOUT_TIME_UNIT)
            .writeTimeout(HttpConstants.TIMEOUT_IO, HttpConstants.TIMEOUT_TIME_UNIT)
            .build()
    }

    @Provides
    fun provideRetrofit(httpClient: OkHttpClient, moshi: Moshi): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.API_BASE_URL)
            .addCallAdapterFactory(RxErrorHandlingCallAdapterFactory(Schedulers.io()))
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(httpClient)
            .build()
    }

    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { message ->
            Timber.tag("OkHttp")
                .d(message)
        })
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return loggingInterceptor
    }

    @Provides
    fun provideJobWebApi(retrofit: Retrofit): JobWebApi {
        return retrofit.create(JobWebApi::class.java)
    }
}