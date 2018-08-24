package com.nerdery.rtaza.mvvmdemo.ui.util

import android.support.annotation.ColorRes
import android.support.annotation.DrawableRes
import com.nerdery.rtaza.mvvmdemo.R
import com.nerdery.rtaza.mvvmdemo.data.local.model.JobStatus
import com.nerdery.rtaza.mvvmdemo.data.local.model.JobTask

const val JUDICIOUSLY_LATE_IN_MINUTES = 1
const val EXCEEDINGLY_LATE_IN_MINUTES = 11

class JobIconUtil {

    companion object {

        @DrawableRes
        fun getTaskIconId(task: JobTask): Int {
            return when (task) {
                JobTask.ELECTRICAL -> R.drawable.icon_electrical
                JobTask.HVAC -> R.drawable.icon_hvac
                JobTask.PLUMBING -> R.drawable.icon_plumbing
                JobTask.CARPENTRY -> R.drawable.icon_carpentry
                JobTask.PAINT -> R.drawable.icon_paint
            }
        }

        @ColorRes
        fun getEtaStatusColorId(status: JobStatus, promisedEta: Long?): Int {
            if (status === JobStatus.NEW || status === JobStatus.ON_SITE) {
                return R.color.job_eta_inapplicable
            }

            if (promisedEta == null) {
                return R.color.job_eta_unknown
            }

            val minutesLate = TimeUtil.calculateMinutesLate(promisedEta)
            return when {
                minutesLate < JUDICIOUSLY_LATE_IN_MINUTES -> R.color.job_eta_on_time
                minutesLate <= EXCEEDINGLY_LATE_IN_MINUTES -> R.color.job_eta_late
                else -> R.color.job_eta_exceedingly_late
            }
        }
    }
}