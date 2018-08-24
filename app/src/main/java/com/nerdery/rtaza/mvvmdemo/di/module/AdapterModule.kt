package com.nerdery.rtaza.mvvmdemo.di.module

import com.nerdery.rtaza.mvvmdemo.ui.jobs.JobsListAdapter
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable

@Module
class AdapterModule {

    @Provides
    fun provideJobsAdapter(): JobsListAdapter {
        return JobsListAdapter(CompositeDisposable())
    }
}