package com.nerdery.rtaza.mvvmdemo.ui.util

fun <T> Collection<T>?.isNullOrEmpty(): Boolean {
    return this == null || isEmpty()
}