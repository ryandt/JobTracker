package com.nerdery.rtaza.jobtracker.ui.util

import android.content.Context
import com.nerdery.rtaza.jobtracker.R

class TextFormatter {

    companion object {

        fun formatFullName(context: Context, firstName: String?, lastName: String?): String {
            val joinedStrings = String.join(" ", firstName, lastName)

            return joinedStrings ?: context.getString(R.string.data_not_available)
        }

        fun formatDueInTime(context: Context, dueInTime: Int): String {
            return when {
                dueInTime > 0 -> context.resources.getQuantityString(R.plurals.minutes, dueInTime, dueInTime)
                dueInTime < 0 -> context.getString(R.string.eta_negative)
                else -> context.getString(R.string.eta_now)
            }
        }
    }
}