package com.nerdery.rtaza.jobtracker.data.local.dao

import androidx.room.*
import com.nerdery.rtaza.jobtracker.data.local.model.Job
import com.nerdery.rtaza.jobtracker.data.local.model.JobWithRelations
import io.reactivex.Flowable
import io.reactivex.Maybe

private const val QUERY_SELECT_ALL = """
    SELECT job.*, worker.id as worker_id, worker.firstName as worker_firstName, worker.lastName as worker_lastName
    FROM job INNER JOIN worker ON job.workerId = worker.id
    WHERE active = :active
    ORDER BY status DESC, eta ASC"""

private const val QUERY_JOB_ID = """
    SELECT job.*, worker.id as worker_id, worker.firstName as worker_firstName, worker.lastName as worker_lastName
    FROM job INNER JOIN worker ON job.workerId = worker.id
    WHERE job.id = :jobId"""

/**
 * Data Access Object for defining Room database operations that can be performed on [Job] entities and entities related
 * to [Job]s, such as a [Job]'s assigned [com.nerdery.rtaza.jobtracker.data.local.model.Worker].
 */
@Dao
abstract class JobDao : JobLocalDataSource {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract override fun insert(job: Job)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract override fun insertAll(jobs: List<Job>)

    @Query(value = QUERY_JOB_ID)
    // Only queries the fields from worker that are pertinent to job-level details
    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    abstract override fun load(jobId: Long): Maybe<JobWithRelations>

    @Query(value = QUERY_SELECT_ALL)
    // Only queries the fields from Worker that are pertinent to job-level details
    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    abstract override fun loadAll(active: Boolean): Maybe<List<JobWithRelations>>

    override fun stream(jobId: Long): Flowable<JobWithRelations> {
        return streamInternal(jobId).distinctUntilChanged()
    }

    @Query(value = QUERY_JOB_ID)
    // Only queries the fields from Worker that are pertinent to job-level details
    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    protected abstract fun streamInternal(jobId: Long): Flowable<JobWithRelations>

    override fun streamAll(active: Boolean): Flowable<List<JobWithRelations>> {
        return streamAllInternal(active).distinctUntilChanged()
    }

    @Query(value = QUERY_SELECT_ALL)
    // Only queries the fields from worker that are pertinent to job-level details
    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    protected abstract fun streamAllInternal(active: Boolean): Flowable<List<JobWithRelations>>
}