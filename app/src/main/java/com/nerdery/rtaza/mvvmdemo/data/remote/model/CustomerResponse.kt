package com.nerdery.rtaza.mvvmdemo.data.remote.model

import com.nerdery.rtaza.mvvmdemo.data.local.model.Customer

data class CustomerResponse(
    val firstName: String?,
    val lastName: String?,
    val phoneNumber: String?
) {
    fun toCustomer(): Customer {
        return Customer(firstName, lastName, phoneNumber)
    }
}