package com.nerdery.rtaza.mvvmdemo.data.local

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import com.nerdery.rtaza.mvvmdemo.data.local.dao.JobDao
import com.nerdery.rtaza.mvvmdemo.data.local.dao.WorkerDao
import com.nerdery.rtaza.mvvmdemo.data.local.model.Job
import com.nerdery.rtaza.mvvmdemo.data.local.model.JobStatus
import com.nerdery.rtaza.mvvmdemo.data.local.model.JobTask
import com.nerdery.rtaza.mvvmdemo.data.local.model.Worker

@Database(
    entities = [
        Job::class,
        Worker::class
    ], version = 3
)
@TypeConverters(
    JobStatus.Converter::class,
    JobTask.Converter::class
)
abstract class ApplicationDatabase : RoomDatabase() {

    abstract fun jobDao(): JobDao

    abstract fun workerDao(): WorkerDao
}