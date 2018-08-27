package com.nerdery.rtaza.mvvmdemo.data.repository

import com.nerdery.rtaza.mvvmdemo.data.core.Error
import com.nerdery.rtaza.mvvmdemo.data.core.Resource
import com.nerdery.rtaza.mvvmdemo.data.local.dao.JobLocalDataSource
import com.nerdery.rtaza.mvvmdemo.data.local.dao.WorkerLocalDataSource
import com.nerdery.rtaza.mvvmdemo.data.local.model.JobWithRelations
import com.nerdery.rtaza.mvvmdemo.data.remote.api.JobWebApi
import com.nerdery.rtaza.mvvmdemo.data.remote.model.JobResponse
import com.nerdery.rtaza.mvvmdemo.data.util.JobResponseFactory
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.Single
import javax.inject.Inject

class JobRepository @Inject constructor(
    private val jobRemoteDataSource: JobWebApi,
    private val jobLocalDataSource: JobLocalDataSource,
    private val workerLocalDataSource: WorkerLocalDataSource
) {

    fun getJobs(active: Boolean): Observable<Resource<List<JobWithRelations>>> {
        return object : PersistableNetworkResourceCall<List<JobResponse>, List<JobWithRelations>>() {
            override fun loadFromDatabase(): Maybe<List<JobWithRelations>> = jobLocalDataSource.loadAll(active)

            override fun createNetworkCall(): Single<List<JobResponse>> = jobRemoteDataSource.fetchAll(active)

            override fun onNetworkCallSuccess(
                emitter: ObservableEmitter<Resource<List<JobWithRelations>>>,
                response: List<JobResponse>
            ) {
                updateLocalDataSources(response)
                emitter.setDisposable(jobLocalDataSource.streamAll(active)
                    .subscribe { jobs ->
                        emitter.onNext(Resource.Success(jobs))
                    })
            }

            // The network request is expected to fail since the URL that's being hit isn't valid.
            // Insert some fake data here for now.
            // TODO: Remove this method override after a local or remote data server is set up to return data
            override fun onNetworkCallError(
                emitter: ObservableEmitter<Resource<List<JobWithRelations>>>,
                error: Error
            ) {
                val response = listOf(
                    JobResponseFactory.createAcceptedHvacJobResponse(),
                    JobResponseFactory.createAcceptedElectricalJobResponse(),
                    JobResponseFactory.createEnRoutePlumbingJobResponse()
                )

                updateLocalDataSources(response)
                emitter.setDisposable(jobLocalDataSource.streamAll(active)
                    .subscribe { jobs ->
                        emitter.onNext(Resource.Success(jobs))
                    })
            }
        }.resourceObservable
    }

    fun getJob(jobId: Long): Observable<Resource<JobWithRelations>> {
        return object : PersistableNetworkResourceCall<JobResponse, JobWithRelations>() {
            override fun loadFromDatabase(): Maybe<JobWithRelations> = jobLocalDataSource.load(jobId)

            override fun createNetworkCall(): Single<JobResponse> = jobRemoteDataSource.fetch(jobId)

            override fun onNetworkCallSuccess(
                emitter: ObservableEmitter<Resource<JobWithRelations>>,
                response: JobResponse
            ) {
                updateLocalDataSources(response)
                emitter.setDisposable(jobLocalDataSource.stream(jobId)
                    .subscribe { jobWithRelations ->
                        emitter.onNext(Resource.Success(jobWithRelations))
                    })
            }
        }.resourceObservable
    }

    private fun updateLocalDataSources(jobsResponse: List<JobResponse>) {
        // Pull out all non-null worker entities assigned to these jobs so they can be inserted into the worker database
        val workers = jobsResponse.map { workerResponse ->
            workerResponse.workerResponse
        }.mapNotNull { workerResponse ->
            workerResponse?.toWorker()
        }

        workerLocalDataSource.insertAll(workers)
        jobLocalDataSource.insertAll(jobsResponse.map { jobResponse ->
            jobResponse.toJob()
        })
    }

    private fun updateLocalDataSources(jobResponse: JobResponse) {
        // Pull out the worker entity assigned to this job (if it's not null) so it can be inserted into the worker database
        jobResponse.workerResponse?.let { workerResponse ->
            workerLocalDataSource.insert(workerResponse.toWorker())
        }
        jobLocalDataSource.insert(jobResponse.toJob())
    }
}