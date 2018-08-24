package com.nerdery.rtaza.mvvmdemo.ui.util

import android.content.Context
import com.nerdery.rtaza.mvvmdemo.R
import com.nerdery.rtaza.mvvmdemo.data.local.model.Vehicle

class TextFormatter {

    companion object {

        fun formatFullName(context: Context, firstName: String?, lastName: String?): String {
            val joinedStrings = String.join(" ", firstName, lastName)

            return if (joinedStrings.isEmpty()) {
                context.getString(R.string.data_not_available)
            } else {
                joinedStrings
            }
        }

        fun formatVehicleInformation(context: Context, vehicle: Vehicle?): String {
            val joinedStrings = String.join(" ", vehicle?.year, vehicle?.color, vehicle?.make, vehicle?.model)

            return if (joinedStrings.isEmpty()) {
                context.getString(R.string.data_not_available)
            } else {
                joinedStrings
            }
        }

        fun formatDueInTime(context: Context, dueInTime: Long): String {
            return when {
                dueInTime > 0 -> context.getString(R.string.minutes, dueInTime)
                dueInTime < 0 -> context.getString(R.string.eta_negative)
                else -> context.getString(R.string.eta_now)
            }
        }
    }
}