package com.nerdery.rtaza.mvvmdemo.di.module

import com.nerdery.rtaza.mvvmdemo.di.ActivityScope
import com.nerdery.rtaza.mvvmdemo.ui.jobs.JobsActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ApplicationModule {

    @ActivityScope
    @ContributesAndroidInjector
    abstract fun contributeMainActivityInjector(): JobsActivity
}