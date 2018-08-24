package com.nerdery.rtaza.mvvmdemo.data.remote.model

import com.nerdery.rtaza.mvvmdemo.data.local.model.Vehicle

data class VehicleResponse(
    val color: String?,
    val make: String?,
    val model: String?,
    val year: String?
) {

    fun toVehicle(): Vehicle {
        return Vehicle(color, make, model, year)
    }
}