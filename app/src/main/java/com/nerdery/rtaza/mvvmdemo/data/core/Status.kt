package com.nerdery.rtaza.mvvmdemo.data.core

/**
 * Status of a network request to get a [Resource].
 */
enum class Status {
    /**
     * The [Resource] is being loaded from a network-bound source.
     */
    LOADING,
    /**
     * The [Resource] was successfully loaded from a network-bound source.
     */
    SUCCESS,
    /**
     * The [Resource] failed to be loaded from a network-bound source.
     */
    ERROR
}