package com.nerdery.rtaza.mvvmdemo.di.module

import com.nerdery.rtaza.mvvmdemo.ui.jobs.JobsListAdapter
import dagger.Module
import dagger.Provides

@Module
class AdapterModule {

    @Provides
    fun provideJobsListAdapter(): JobsListAdapter {
        return JobsListAdapter()
    }
}