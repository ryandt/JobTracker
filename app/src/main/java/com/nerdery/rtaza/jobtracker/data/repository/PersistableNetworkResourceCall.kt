package com.nerdery.rtaza.jobtracker.data.repository

import com.nerdery.rtaza.jobtracker.data.core.Error
import com.nerdery.rtaza.jobtracker.data.core.Resource
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import timber.log.Timber

/**
 * Abstracts the logic for making a network call that returns a resource that is persisted as a resource. Note that this
 * class handles two different data types because the data type returned from the web API, [ResponseType], may not match
 * the data type used in the local database, [ResourceType].
 *
 * Step 1. Emits loading.
 * Step 2. Attempts to load resource from local database.
 * Step 3. If load returns a resource, emits loading with that resource.
 *         If load completes with an error, continues.
 *         If load returns no resource, continues.
 * Step 4. Attempts to fetch item from network.
 * Step 5. If the network call fails, emits the [Error].
 *
 * All operations are executed on the TIMEOUT_IO [io.reactivex.Scheduler].
 *
 * @param ResponseType type of the response returned from the network call.
 * @param ResourceType type of the resource to be stored in the database and to be emitted.
 */
abstract class PersistableNetworkResourceCall<ResponseType, ResourceType> :
    NetworkCall<ResponseType, Resource<ResourceType>>() {
    val resourceObservable: Observable<Resource<ResourceType>>

    init {
        resourceObservable = Observable.create<Resource<ResourceType>> { emitter ->
            emitter.onNext(Resource.Loading(null))
            loadFromDatabase()
                .subscribe({ queryResult ->
                    emitter.onNext(Resource.Loading(queryResult))
                    fetchFromNetwork(emitter)
                }, { error ->
                    Timber.e(error)
                    fetchFromNetwork(emitter)
                }, {
                    fetchFromNetwork(emitter)
                })
        }
    }

    /**
     * Called to execute a local database query and return the result of that query.
     *
     * @return the result of the [Maybe] from the database query stream.
     */
    protected abstract fun loadFromDatabase(): Maybe<ResourceType>

    /**
     * Emits the [error] from the network call. Override this if need to handle the [error] differently.
     */
    override fun onNetworkCallError(emitter: ObservableEmitter<Resource<ResourceType>>, error: Error) {
        emitter.onNext(Resource.Error(error))
    }
}