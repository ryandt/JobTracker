package com.nerdery.rtaza.jobtracker.data.remote.model

import com.nerdery.rtaza.jobtracker.data.local.model.Job
import com.nerdery.rtaza.jobtracker.data.local.model.JobStatus
import com.nerdery.rtaza.jobtracker.data.local.model.JobTask

data class JobResponse(
    val id: Long,
    val task: String,
    val status: String,
    val locationResponse: LocationResponse,
    val workerResponse: WorkerResponse?,
    val customerResponse: CustomerResponse,
    val eta: Long,
    val customerNotes: String?
) {

    fun toJob(): Job {
        val jobStatus = JobStatus.Converter().toJobStatus(status)
        return Job(
            id,
            JobTask.Converter().toJobTask(task),
            jobStatus,
            jobStatus.active,
            customerResponse.toCustomer(),
            locationResponse.toLocation(),
            workerResponse?.id,
            eta,
            customerNotes
        )
    }
}