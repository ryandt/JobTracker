package com.nerdery.rtaza.jobtracker.data.remote.model

import com.nerdery.rtaza.jobtracker.data.local.model.Customer

data class CustomerResponse(
    val firstName: String,
    val lastName: String,
    val phoneNumber: String
) {

    fun toCustomer(): Customer {
        return Customer(firstName, lastName, phoneNumber)
    }
}