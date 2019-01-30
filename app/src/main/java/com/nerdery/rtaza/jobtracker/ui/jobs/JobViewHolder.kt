package com.nerdery.rtaza.jobtracker.ui.jobs

import android.view.View
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.nerdery.rtaza.jobtracker.ui.util.*
import kotlinx.android.synthetic.main.item_job.view.*

class JobViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private lateinit var presentationModel: JobsViewModel.Presentation.Model

    fun bind(presentationModel: JobsViewModel.Presentation.Model) {
        this.presentationModel = presentationModel
        itemView.apply {
            taskImageView.setImageResource(presentationModel.jobTaskIconId)
            addressLine1TextView.text = presentationModel.addressLine1Text
            addressLine2TextView.text = presentationModel.addressLine2Text
            taskTypeTextView.text = context.getString(presentationModel.jobTaskTextId)
            statusTextView.text = context.getString(presentationModel.jobStatusTextId)
            workerTextView.text = presentationModel.workerNameText
            updateEta()
        }
        itemView.setOnClickListener {
            // TODO: Start detail Activity
        }
    }

    fun updateEta() {
        itemView.apply {
            val dueInTime = TimeUtil.calculateDueInTime(presentationModel.eta)
            etaTextView.text = TextFormatter.formatDueInTime(itemView.context, dueInTime)
            @ColorInt val etaStatusColor = ContextCompat.getColor(
                context, JobIconUtil.getEtaStatusColorId(presentationModel.jobStatus, presentationModel.eta)
            )
            taskImageView.setBackgroundTint(etaStatusColor)

            if (dueInTime < 0) {
                etaTextView.setTextColor(etaStatusColor)
            } else {
                etaTextView.setTextColor(context.resolveThemeAttribute(android.R.attr.textColorPrimary))
            }
        }
    }
}