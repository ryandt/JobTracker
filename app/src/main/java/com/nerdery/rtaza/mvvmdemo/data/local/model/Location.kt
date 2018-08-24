package com.nerdery.rtaza.mvvmdemo.data.local.model

data class Location(
    val street: String,
    val city: String,
    val zip: String,
    val state: String,
    val latitude: Double,
    val longitude: Double
)