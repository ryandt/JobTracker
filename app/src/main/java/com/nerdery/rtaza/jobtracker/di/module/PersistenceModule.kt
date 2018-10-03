package com.nerdery.rtaza.jobtracker.di.module

import android.app.Application
import android.arch.persistence.room.Room
import com.nerdery.rtaza.jobtracker.data.local.ApplicationDatabase
import com.nerdery.rtaza.jobtracker.data.local.dao.JobLocalDataSource
import com.nerdery.rtaza.jobtracker.data.local.dao.WorkerLocalDataSource
import dagger.Module
import dagger.Provides

@Module
class PersistenceModule {

    @Provides
    fun provideRoomDatabase(application: Application): ApplicationDatabase {
        return Room.databaseBuilder(application, ApplicationDatabase::class.java, "job-tracker-database")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideJobLocalDataSource(database: ApplicationDatabase): JobLocalDataSource {
        return database.jobDao()
    }

    @Provides
    fun provideWorkerLocalDataSource(database: ApplicationDatabase): WorkerLocalDataSource {
        return database.workerDao()
    }
}