package com.nerdery.rtaza.mvvmdemo.data.remote.model

import com.nerdery.rtaza.mvvmdemo.data.local.model.Worker

data class WorkerResponse(
    val id: String,
    val firstName: String,
    val lastName: String,
    val onDuty: Boolean
) {

    fun toWorker(): Worker {
        return Worker(id, firstName, lastName, onDuty)
    }
}