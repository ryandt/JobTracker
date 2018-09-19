package com.nerdery.rtaza.jobtracker.data.remote.model

import com.nerdery.rtaza.jobtracker.data.local.model.Location

data class LocationResponse(
    val street: String,
    val city: String,
    val zip: String,
    val state: String,
    val latitude: Double,
    val longitude: Double
) {

    fun toLocation(): Location {
        return Location(street, city, zip, state, latitude, longitude)
    }
}