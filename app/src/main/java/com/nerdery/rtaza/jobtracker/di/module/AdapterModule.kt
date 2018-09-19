package com.nerdery.rtaza.jobtracker.di.module

import com.nerdery.rtaza.jobtracker.ui.jobs.JobsListAdapter
import dagger.Module
import dagger.Provides

@Module
class AdapterModule {

    @Provides
    fun provideJobsListAdapter(): JobsListAdapter {
        return JobsListAdapter()
    }
}