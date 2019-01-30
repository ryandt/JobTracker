package com.nerdery.rtaza.jobtracker.data.local.model

import androidx.room.Embedded

data class JobWithRelations(
    @Embedded var job: Job,
    @Embedded(prefix = "worker_") var worker: WorkerRelation?
)

data class WorkerRelation(
    val id: Long,
    val firstName: String,
    val lastName: String
) {
    constructor(worker: Worker) : this(worker.id, worker.firstName, worker.lastName)
}