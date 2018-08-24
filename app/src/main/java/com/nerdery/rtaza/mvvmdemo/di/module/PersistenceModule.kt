package com.nerdery.rtaza.mvvmdemo.di.module

import android.app.Application
import android.arch.persistence.room.Room
import com.nerdery.rtaza.mvvmdemo.data.local.ApplicationDatabase
import com.nerdery.rtaza.mvvmdemo.data.local.dao.JobLocalDataSource
import com.nerdery.rtaza.mvvmdemo.data.local.dao.WorkerLocalDataSource
import dagger.Module
import dagger.Provides

@Module
class PersistenceModule {

    @Provides
    fun provideRoomDatabase(application: Application): ApplicationDatabase {
        return Room.databaseBuilder(application, ApplicationDatabase::class.java, "mvvm-demo-database")
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