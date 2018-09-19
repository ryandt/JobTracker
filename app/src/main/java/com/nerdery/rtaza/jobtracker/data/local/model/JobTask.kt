package com.nerdery.rtaza.jobtracker.data.local.model

import android.arch.persistence.room.TypeConverter
import android.support.annotation.StringRes
import com.nerdery.rtaza.jobtracker.R

enum class JobTask(
    val id: String,
    @StringRes val label: Int
) {
    ELECTRICAL("electrical", R.string.job_task_electrical),
    HVAC("hvac", R.string.job_task_hvac),
    PLUMBING("plumbing", R.string.job_task_plumbing),
    CARPENTRY("carpentry", R.string.job_task_carpentry),
    PAINT("paint", R.string.job_task_paint);

    class Converter {

        fun toJobTask(id: String): JobTask {
            var task = PAINT

            for (value in JobTask.values()) {
                if (value.id == id) {
                    task = value
                }
            }

            return task
        }

        @TypeConverter
        fun toJobTask(ordinal: Int): JobTask {
            return values()[ordinal]
        }

        @TypeConverter
        fun toOrdinal(task: JobTask): Int {
            return task.ordinal
        }
    }
}