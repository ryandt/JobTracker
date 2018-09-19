package com.nerdery.rtaza.jobtracker.di.module

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.nerdery.rtaza.jobtracker.di.ViewModelFactory
import com.nerdery.rtaza.jobtracker.di.ViewModelKey
import com.nerdery.rtaza.jobtracker.ui.jobs.JobsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(JobsViewModel::class)
    abstract fun bindJobsViewModel(viewModel: JobsViewModel): ViewModel
}