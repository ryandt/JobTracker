package com.nerdery.rtaza.jobtracker.di

import android.app.Application
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import javax.inject.Inject
import javax.inject.Provider

/**
 * Factory responsible for creating all [ViewModel]s.
 */
class ViewModelFactory @Inject constructor(
    private val creators: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>,
    application: Application
) : ViewModelProvider.AndroidViewModelFactory(application) {

    override fun <ViewModelType : ViewModel> create(viewModelClass: Class<ViewModelType>): ViewModelType {
        var creator: Provider<ViewModel>? = creators[viewModelClass]
        if (creator == null) {
            for ((key, value) in creators) {
                if (viewModelClass.isAssignableFrom(key)) {
                    creator = value
                    break
                }
            }
        }

        if (creator == null) throw IllegalArgumentException("Unknown ViewModel class $viewModelClass")
        @Suppress("UNCHECKED_CAST")
        return creator.get() as ViewModelType
    }
}