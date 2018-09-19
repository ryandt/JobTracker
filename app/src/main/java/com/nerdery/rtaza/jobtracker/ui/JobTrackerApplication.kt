package com.nerdery.rtaza.jobtracker.ui

import android.app.Activity
import android.app.Application
import com.nerdery.rtaza.jobtracker.BuildConfig
import com.nerdery.rtaza.jobtracker.di.component.DaggerApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import io.reactivex.exceptions.CompositeException
import io.reactivex.exceptions.OnErrorNotImplementedException
import io.reactivex.exceptions.UndeliverableException
import io.reactivex.plugins.RxJavaPlugins
import timber.log.Timber
import javax.inject.Inject

class JobTrackerApplication : Application(), HasActivityInjector {
    @Inject lateinit var activityInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        DaggerApplicationComponent.builder()
            .application(this)
            .build()
            .inject(this)
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        setUncaughtRxJavaErrorHandler()
    }

    /**
     * Handles and logs all uncaught exceptions thrown through any RxJava stream in the application that does not
     * override onError. This prevents the app from crashing if a stream that doesn't override onError receives an
     * exception.
     */
    private fun setUncaughtRxJavaErrorHandler() {
        RxJavaPlugins.setErrorHandler { throwable ->
            val cause: Throwable? =
                if (throwable is OnErrorNotImplementedException || throwable is UndeliverableException) {
                    throwable.cause
                } else {
                    throwable
                }

            if (throwable is CompositeException) {
                for (exception in throwable.exceptions) {
                    Timber.e(exception)
                }
            } else {
                Timber.e(cause)
            }
        }
    }

    override fun activityInjector(): AndroidInjector<Activity> {
        return activityInjector
    }
}