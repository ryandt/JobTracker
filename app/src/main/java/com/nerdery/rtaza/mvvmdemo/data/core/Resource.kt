package com.nerdery.rtaza.mvvmdemo.data.core

/**
 * A wrapper for data that is requested from a data source.
 *
 * @param status the current status ([Status.LOADING], [Status.RESOURCE_FOUND], [Status.NO_RESOURCE_FOUND], or
 * [Status.ERROR]) of the Resource at any given time.
 * @param data the object that the Resource wraps. [Status.LOADING] and [Status.RESOURCE_FOUND] could each have different
 * representations of the data.
 * @param error the [com.nerdery.rtaza.mvvmdemo.data.core.Error] that the Resource wraps in the case that the Resource
 * has a status of [Status.ERROR].
 */
sealed class Resource<DataType>(
    val status: Status,
    val data: DataType?,
    val error: com.nerdery.rtaza.mvvmdemo.data.core.Error?
) {

    class Loading<DataType>(loadingData: DataType?) : Resource<DataType>(Status.LOADING, loadingData, null)

    class ResourceFound<DataType>(successData: DataType) : Resource<DataType>(Status.RESOURCE_FOUND, successData, null)

    class NoResourceFound<DataType> : Resource<DataType>(Status.NO_RESOURCE_FOUND, null, null)

    class Error<DataType>(error: com.nerdery.rtaza.mvvmdemo.data.core.Error) :
        Resource<DataType>(Status.ERROR, null, error)
}