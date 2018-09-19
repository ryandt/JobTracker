package com.nerdery.rtaza.jobtracker.ui.util

fun <T> Collection<T>?.isNullOrEmpty(): Boolean {
    return this == null || isEmpty()
}