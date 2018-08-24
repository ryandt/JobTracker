package com.nerdery.rtaza.mvvmdemo.data.remote.model

import com.nerdery.rtaza.mvvmdemo.data.local.model.Job
import com.nerdery.rtaza.mvvmdemo.data.local.model.JobStatus
import com.nerdery.rtaza.mvvmdemo.data.local.model.JobTask

data class JobResponse(
    val id: Long,
    val task: String,
    val status: String,
    val active: Boolean,
    val locationResponse: LocationResponse,
    val workerResponse: WorkerResponse?,
    val customerResponse: CustomerResponse?,
    val eta: Long,
    val notes: String?
) {

    fun toJob(): Job {
        return Job(
            id = id,
            task = JobTask.Converter().toJobTask(task),
            status = JobStatus.Converter().toJobStatus(status),
            active = active,
            location = locationResponse.toLocation(),
            workerId = workerResponse?.id,
            customer = customerResponse?.toCustomer(),
            eta = eta,
            notes = notes
        )
    }
}