package com.nerdery.rtaza.mvvmdemo.ui.util

import java.util.concurrent.TimeUnit

class TimeUtil {

    companion object {

        fun getCurrentTimeMillis() = System.currentTimeMillis()

        /**
         * Calculates the difference between the [promisedEta] and the current time.
         *
         * @param promisedEta the promised ETA in milliseconds.
         * @return the difference between the [promisedEta] and the current time in minutes.
         */
        fun calculateDueInTime(promisedEta: Long): Long {
            return TimeUnit.MILLISECONDS.toMinutes(promisedEta) -
                    TimeUnit.MILLISECONDS.toMinutes(getCurrentTimeMillis())
        }

        /**
         * Calculates the difference between the the current time and the [promisedEta].
         *
         * @param promisedEta the promised ETA in milliseconds.
         * @return the difference between the current time and the [promisedEta] in minutes.
         * Returns 0 if the difference is negative (not late).
         */
        fun calculateMinutesLate(promisedEta: Long): Long {
            val diff = TimeUnit.MILLISECONDS.toMinutes(getCurrentTimeMillis()) -
                    TimeUnit.MILLISECONDS.toMinutes(promisedEta)
            return if (diff > 0) diff else 0
        }
    }
}