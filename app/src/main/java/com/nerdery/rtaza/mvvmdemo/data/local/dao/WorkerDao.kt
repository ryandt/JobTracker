package com.nerdery.rtaza.mvvmdemo.data.local.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.nerdery.rtaza.mvvmdemo.data.local.model.Worker
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