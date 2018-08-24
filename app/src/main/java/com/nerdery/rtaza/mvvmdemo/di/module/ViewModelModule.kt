package com.nerdery.rtaza.mvvmdemo.di.module

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.nerdery.rtaza.mvvmdemo.di.ViewModelFactory
import com.nerdery.rtaza.mvvmdemo.di.ViewModelKey
import com.nerdery.rtaza.mvvmdemo.ui.jobs.JobsViewModel
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