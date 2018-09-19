package com.nerdery.rtaza.jobtracker.data.local.dao

import com.nerdery.rtaza.jobtracker.data.local.model.Worker
import io.reactivex.Flowable
import io.reactivex.Maybe

interface WorkerLocalDataSource {

    fun insert(worker: Worker)

    fun insertAll(workers: List<Worker>)

    fun load(workerId: String): Maybe<Worker>

    fun stream(workerId: String): Flowable<List<Worker>>
}