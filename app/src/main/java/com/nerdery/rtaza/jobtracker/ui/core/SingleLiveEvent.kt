package com.nerdery.rtaza.jobtracker.ui.core

import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import timber.log.Timber
import java.util.concurrent.atomic.AtomicBoolean

/**
 * A lifecycle-aware observable that sends only new updates after subscription. Used for events like navigation and
 * Toast messages.
 *
 * This avoids a common problem with LiveData: on configuration change (like rotation of the device), an update can
 * be emitted if the observer is active. This LiveData only calls the observable if there's an explicit call to
 * setValue() or call().
 *
 * Note that only one observer is going to be notified of changes.
 */
class SingleLiveEvent<DataType> : MutableLiveData<DataType>() {
    private val pending = AtomicBoolean(false)

    @MainThread
    override fun observe(owner: LifecycleOwner, observer: Observer<in DataType>) {
        if (hasActiveObservers()) {
            Timber.w("Multiple observers registered but only one will be notified of changes")
        }

        // Observe the internal MutableLiveData
        super.observe(owner, Observer { data ->
            if (pending.compareAndSet(true, false)) {
                observer.onChanged(data)
            }
        })
    }

    @MainThread
    override fun setValue(value: DataType?) {
        pending.set(true)
        super.setValue(value)
    }

    /**
     * Used for cases where T is Void, to make calls cleaner.
     */
    @MainThread
    fun call() {
        value = null
    }
}