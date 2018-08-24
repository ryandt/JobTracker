package com.nerdery.rtaza.mvvmdemo.data.local.model

import android.arch.persistence.room.*

@Entity(
    foreignKeys = [(ForeignKey(
        entity = Worker::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("workerId")
    ))],
    indices = [(Index("workerId"))]
)
data class Job(
    @PrimaryKey var id: Long,
    var task: JobTask,
    var status: JobStatus,
    var active: Boolean,
    @Embedded var customer: Customer,
    @Embedded var location: Location,
    var workerId: String?,
    var eta: Long,
    var notes: String?
)