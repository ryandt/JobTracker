package com.nerdery.rtaza.mvvmdemo.data.remote.model

import com.nerdery.rtaza.mvvmdemo.data.local.model.Worker

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