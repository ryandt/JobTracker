package com.nerdery.rtaza.jobtracker.data.repository

import android.annotation.SuppressLint
import com.nerdery.rtaza.jobtracker.data.core.Error
import io.reactivex.ObservableEmitter
import io.reactivex.Single
import timber.log.Timber

/**
 * Abstracts the logic for making a network call. This class forwards the request's result payload or error to
 * [onNetworkCallSuccess] or [onNetworkCallError], respectively.
 *
 * @param ResponseType type of the response returned from the network call.
 * @param EmissionType type expected by the [ObservableEmitter].
 */
abstract class NetworkCall<ResponseType, EmissionType> {

    /**
     * Override this method and return the response of the network call.
     *
     * @return the [Single] from the network call stream.
     */
    protected abstract fun createNetworkCall(): Single<ResponseType>

    /**
     * Override this method to handle the success response of the network call.
     *
     * @param emitter the [ObservableEmitter] of the calling [io.reactivex.Observable]. This is passed as a param to
     * allow subclasses to override the flow of the [io.reactivex.Observable] based on the response of this call.
     * @param response the success response of the network call.
     */
    protected abstract fun onNetworkCallSuccess(emitter: ObservableEmitter<EmissionType>, response: ResponseType)

    /**
     * Override this method to handle the error returned from the network call.
     *
     * @param emitter the [ObservableEmitter] of the calling [io.reactivex.Observable]. This is passed as a param to
     * allow subclasses to override the flow of the [io.reactivex.Observable] based on the error from this call.
     * @param error the mapped [Error] thrown from the call.
     */
    protected abstract fun onNetworkCallError(emitter: ObservableEmitter<EmissionType>, error: Error)

    /**
     * Performs the network call defined in [createNetworkCall].
     * This method should be called from within an [io.reactivex.Observable].
     *
     * @param emitter the [ObservableEmitter] of the calling [io.reactivex.Observable].
     */
    @SuppressLint("CheckResult") // The Single's result is disposed in the onSuccess or onError
    protected open fun fetchFromNetwork(emitter: ObservableEmitter<EmissionType>) {
        createNetworkCall()
            .subscribe({ response ->
                onNetworkCallSuccess(emitter, response)
            }, { error ->
                if (error is Error) {
                    onNetworkCallError(emitter, error)
                } else {
                    // This should never happen since all network exceptions are caught and typecasted in
                    // RxErrorHandlingCallAdapterFactory
                    Timber.wtf("fetchFromNetwork() returned unknown error: $error")
                }
            })
    }
}