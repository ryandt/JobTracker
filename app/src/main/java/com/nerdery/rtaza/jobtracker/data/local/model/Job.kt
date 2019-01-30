package com.nerdery.rtaza.jobtracker.data.local.model

import androidx.room.*

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
    private var status: JobStatus,
    private var active: Boolean,
    @Embedded var customer: Customer,
    @Embedded var location: Location,
    var workerId: Long?,
    var eta: Long,
    var notes: String?
) {

    fun setStatus(status: JobStatus) {
        this.status = status
        active = status.active
    }

    fun getStatus() = status

    fun getActive() = active
}