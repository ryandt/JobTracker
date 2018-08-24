package com.nerdery.rtaza.mvvmdemo.data.remote.api

import com.nerdery.rtaza.mvvmdemo.data.remote.model.JobResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface JobWebApi {

    @GET("job")
    fun fetch(@Query("jobId") jobId: Long): Single<JobResponse>

    @GET("jobs")
    fun fetchAll(@Query("active") active: Boolean): Single<List<JobResponse>>
}