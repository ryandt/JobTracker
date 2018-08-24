package com.nerdery.rtaza.mvvmdemo.data.local.dao

import android.arch.persistence.room.*
import com.nerdery.rtaza.mvvmdemo.data.local.model.Job
import com.nerdery.rtaza.mvvmdemo.data.local.model.JobWithRelations
import io.reactivex.Flowable
import io.reactivex.Maybe

private const val QUERY_SELECT_ALL = """
    SELECT job.*, workerResponse.id as worker_id, workerResponse.firstName as worker_firstName, workerResponse.lastName as worker_lastName
    FROM job INNER JOIN workerResponse ON job.workerId = workerResponse.id
    WHERE active = :active
    ORDER BY status DESC, eta ASC"""

private const val QUERY_JOB_ID = """
    SELECT job.*, workerResponse.id as worker_id, workerResponse.firstName as worker_firstName, workerResponse.lastName as worker_lastName
    FROM job INNER JOIN workerResponse ON job.workerId = workerResponse.id
    WHERE id = :jobId"""

/**
 * Data Access Object for defining Room database operations that can be performed on [Job] entities and entities related
 * to [Job]s, such as a [Job]'s assigned [com.nerdery.rtaza.mvvmdemo.data.local.model.Worker].
 */
@Dao
abstract class JobDao : JobLocalDataSource {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract override fun insert(job: Job)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract override fun insertAll(jobs: List<Job>)

    @Query(value = QUERY_JOB_ID)
    // Only queries the fields from workerResponse that are pertinent to job-level details
    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    abstract override fun load(jobId: Long): Maybe<JobWithRelations>

    @Query(value = QUERY_SELECT_ALL)
    // Only queries the fields from Worker that are pertinent to job-level details
    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    abstract override fun loadAll(active: Boolean): Maybe<List<JobWithRelations>>

    override fun stream(jobId: Long): Flowable<JobWithRelations> {
        return streamInternal().distinctUntilChanged()
    }

    @Query(value = QUERY_JOB_ID)
    // Only queries the fields from Worker that are pertinent to job-level details
    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    protected abstract fun streamInternal(): Flowable<JobWithRelations>

    override fun streamAll(active: Boolean): Flowable<List<JobWithRelations>> {
        return streamAllInternal().distinctUntilChanged()
    }

    @Query(value = QUERY_SELECT_ALL)
    // Only queries the fields from workerResponse that are pertinent to job-level details
    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    protected abstract fun streamAllInternal(): Flowable<List<JobWithRelations>>
}