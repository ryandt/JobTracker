package com.nerdery.rtaza.mvvmdemo.ui.util

import android.content.Context
import com.nerdery.rtaza.mvvmdemo.R

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

        fun formatDueInTime(context: Context, dueInTime: Int): String {
            return when {
                dueInTime > 0 -> context.resources.getQuantityString(R.plurals.minutes, dueInTime, dueInTime)
                dueInTime < 0 -> context.getString(R.string.eta_negative)
                else -> context.getString(R.string.eta_now)
            }
        }
    }
}