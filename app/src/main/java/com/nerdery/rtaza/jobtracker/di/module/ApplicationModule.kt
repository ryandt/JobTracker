package com.nerdery.rtaza.jobtracker.di.module

import com.nerdery.rtaza.jobtracker.di.ActivityScope
import com.nerdery.rtaza.jobtracker.ui.jobs.JobsActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ApplicationModule {

    @ActivityScope
    @ContributesAndroidInjector
    abstract fun contributeMainActivityInjector(): JobsActivity
}