package com.nerdery.rtaza.mvvmdemo.data.local.model

import android.arch.persistence.room.TypeConverter
import android.support.annotation.StringRes
import com.nerdery.rtaza.mvvmdemo.R

/**
 * The status of a job. The order of the enum values are significant. The ordinal of each value corresponds to the
 * priority of the job. The smaller the ordinal, the lower the priority.
 */
enum class JobStatus(
    val id: String,
    @StringRes val label: Int,
    val active: Boolean
) {
    NEW("new", R.string.job_status_new, false),
    ACCEPTED("accepted", R.string.job_status_accepted, true),
    DECLINED("declined", R.string.job_status_declined, false),
    MISSED("missed", R.string.job_status_missed, false),
    EN_ROUTE("enRoute", R.string.job_status_en_route, true),
    ON_SITE("onSite", R.string.job_status_on_site, true),
    COMPLETED("completed", R.string.job_status_completed, false);

    class Converter {

        fun toJobStatus(id: String): JobStatus {
            var status = NEW

            for (value in JobStatus.values()) {
                if (value.id == id) {
                    status = value
                }
            }

            return status
        }

        @TypeConverter
        fun toJobStatus(ordinal: Int): JobStatus {
            return values()[ordinal]
        }

        @TypeConverter
        fun toOrdinal(status: JobStatus): Int {
            return status.ordinal
        }
    }
}