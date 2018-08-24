package com.nerdery.rtaza.mvvmdemo.data.remote.model

import com.nerdery.rtaza.mvvmdemo.data.local.model.Location

data class LocationResponse(
    val street1: String?,
    val street2: String?,
    val city: String?,
    val zip: String?,
    val state: String?,
    val latitude: Double,
    val longitude: Double
) {

    fun toLocation(): Location {
        return Location(street1, street2, city, zip, state, latitude, longitude)
    }
}