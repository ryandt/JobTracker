package com.nerdery.rtaza.jobtracker.data.repository

import com.nerdery.rtaza.jobtracker.data.core.Error
import com.nerdery.rtaza.jobtracker.data.core.Resource
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.schedulers.Schedulers

/**
 * Abstracts the logic for making a network call that returns a resource.
 *
 * Step 1. Emits loading.
 * Step 2. Attempts to fetch item from network.
 * Step 3. If the network call fails, emits the [Error].
 *
 * All operations are executed on the TIMEOUT_IO [io.reactivex.Scheduler].
 *
 * @param ResourceType type of the resource to be emitted.
 */
abstract class NetworkResourceCall<ResourceType> : NetworkCall<ResourceType, Resource<ResourceType>>() {
    val resourceObservable: Observable<Resource<ResourceType>>

    init {
        resourceObservable = Observable.create<Resource<ResourceType>> { emitter ->
            emitter.onNext(Resource.Loading(null))
            fetchFromNetwork(emitter)
        }.subscribeOn(Schedulers.io())
    }

    /**
     * Emits the [error] from the network call. Override this if need to handle the [error] differently.
     */
    override fun onNetworkCallError(emitter: ObservableEmitter<Resource<ResourceType>>, error: Error) {
        emitter.onNext(Resource.Error(error))
    }
}