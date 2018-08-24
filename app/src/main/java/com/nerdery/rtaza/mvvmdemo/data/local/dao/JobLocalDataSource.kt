package com.nerdery.rtaza.mvvmdemo.data.local.dao

import com.nerdery.rtaza.mvvmdemo.data.local.model.Job
import com.nerdery.rtaza.mvvmdemo.data.local.model.JobWithRelations
import io.reactivex.Flowable
import io.reactivex.Maybe

interface JobLocalDataSource {

    fun insert(job: Job)

    fun insertAll(jobs: List<Job>)

    fun load(jobId: Long): Maybe<JobWithRelations>

    fun loadAll(active: Boolean): Maybe<List<JobWithRelations>>

    fun stream(jobId: Long): Flowable<JobWithRelations>

    fun streamAll(active: Boolean): Flowable<List<JobWithRelations>>
}