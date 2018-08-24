package com.nerdery.rtaza.mvvmdemo.data.local.model

import android.arch.persistence.room.Embedded

data class JobWithRelations(
    @Embedded var job: Job,
    @Embedded(prefix = "worker_") var worker: WorkerRelation?
)

data class WorkerRelation(
    val id: String,
    val firstName: String,
    val lastName: String
) {
    constructor(worker: Worker) : this(worker.id, worker.firstName, worker.lastName)
}