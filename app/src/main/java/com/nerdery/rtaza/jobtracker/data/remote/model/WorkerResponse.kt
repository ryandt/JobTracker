package com.nerdery.rtaza.jobtracker.data.remote.model

import com.nerdery.rtaza.jobtracker.data.local.model.Worker

data class WorkerResponse(
    val id: Long,
    val firstName: String,
    val lastName: String,
    val available: Boolean
) {

    fun toWorker(): Worker {
        return Worker(id, firstName, lastName, available)
    }
}