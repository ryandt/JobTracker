package com.nerdery.rtaza.mvvmdemo.data.core

/**
 * Status of a network request to get a [Resource].
 */
enum class Status {
    /**
     * The [Resource] is being loaded from a source.
     */
    LOADING,
    /**
     * The network request was successful, and the result is not null or empty.
     */
    RESOURCE_FOUND,
    /**
     * The network request was successful, but the result was null or empty.
     */
    NO_RESOURCE_FOUND,
    /**
     * The network request failed with an error.
     */
    ERROR
}