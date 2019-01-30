package com.nerdery.rtaza.jobtracker.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.nerdery.rtaza.jobtracker.data.local.dao.JobDao
import com.nerdery.rtaza.jobtracker.data.local.dao.WorkerDao
import com.nerdery.rtaza.jobtracker.data.local.model.Job
import com.nerdery.rtaza.jobtracker.data.local.model.JobStatus
import com.nerdery.rtaza.jobtracker.data.local.model.JobTask
import com.nerdery.rtaza.jobtracker.data.local.model.Worker

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