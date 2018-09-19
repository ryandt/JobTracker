package com.nerdery.rtaza.jobtracker.ui.util

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LiveData

fun <T> LiveData<T>.observeNonNull(owner: LifecycleOwner, observer: (data: T) -> Unit) {
    this.observe(owner, android.arch.lifecycle.Observer { data ->
        data?.let(observer)
    })
}