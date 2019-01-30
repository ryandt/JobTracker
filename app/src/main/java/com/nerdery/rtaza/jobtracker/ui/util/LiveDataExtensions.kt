package com.nerdery.rtaza.jobtracker.ui.util

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

fun <T> LiveData<T>.observeNonNull(owner: LifecycleOwner, observer: (data: T) -> Unit) {
    this.observe(owner, Observer { data ->
        data?.let(observer)
    })
}