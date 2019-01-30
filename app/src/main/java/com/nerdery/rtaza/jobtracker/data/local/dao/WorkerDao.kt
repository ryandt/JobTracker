package com.nerdery.rtaza.jobtracker.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nerdery.rtaza.jobtracker.data.local.model.Worker
import io.reactivex.Flowable
import io.reactivex.Maybe

private const val QUERY_SELECT_ALL = "SELECT * FROM worker WHERE id = :workerId LIMIT 1"

/**
 * Data Access Object for defining Room database operations that can be performed on [Worker] entities.
 */
@Dao
abstract class WorkerDao : WorkerLocalDataSource {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract override fun insert(worker: Worker)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract override fun insertAll(workers: List<Worker>)

    @Query(QUERY_SELECT_ALL)
    abstract override fun load(workerId: String): Maybe<Worker>

    override fun stream(workerId: String): Flowable<List<Worker>> {
        return streamInternal(workerId).distinctUntilChanged()
    }

    @Query(QUERY_SELECT_ALL)
    protected abstract fun streamInternal(workerId: String): Flowable<List<Worker>>
}