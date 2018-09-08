package com.nerdery.rtaza.mvvmdemo.ui.jobs

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil
import android.view.LayoutInflater
import android.view.ViewGroup
import com.nerdery.rtaza.mvvmdemo.R
import com.nerdery.rtaza.mvvmdemo.ui.util.TimeUtil
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import java.util.concurrent.TimeUnit

private const val ETA_UPDATE_INITIAL_DELAY: Long = 0
private const val ETA_UPDATE_INTERVAL: Long = 1
private val ETA_UPDATE_TIME_UNIT = TimeUnit.MINUTES

class JobsListAdapter(private val compositeDisposable: CompositeDisposable) :
    ListAdapter<JobsViewModel.Presentation.Model, JobViewHolder>(diffUtilItemCallback), LifecycleObserver {

    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JobViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_job, parent, false)
        return JobViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: JobViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onBindViewHolder(holder: JobViewHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position)
        } else {
            for (payload in payloads) {
                when (payload) {
                    is JobsViewModel.Presentation.Model -> onBindViewHolder(holder, position)
                    is Long -> holder.updateEta()
                }
            }
        }
    }

    override fun getItemId(position: Int): Long {
        return getItem(position).jobId
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart() {
        Observable.interval(ETA_UPDATE_INITIAL_DELAY, ETA_UPDATE_INTERVAL, ETA_UPDATE_TIME_UNIT)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                notifyItemRangeChanged(0, itemCount, TimeUtil.getCurrentTimeMillis())
            }.addTo(compositeDisposable)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStop() {
        compositeDisposable.clear()
    }
}

private val diffUtilItemCallback = object : DiffUtil.ItemCallback<JobsViewModel.Presentation.Model>() {
    override fun areItemsTheSame(
        oldModel: JobsViewModel.Presentation.Model,
        newModel: JobsViewModel.Presentation.Model
    ): Boolean {
        return oldModel.jobId == newModel.jobId
    }

    override fun areContentsTheSame(
        oldModel: JobsViewModel.Presentation.Model,
        newModel: JobsViewModel.Presentation.Model
    ): Boolean {
        return oldModel == newModel
    }

    override fun getChangePayload(
        oldItem: JobsViewModel.Presentation.Model,
        newItem: JobsViewModel.Presentation.Model
    ): Any {
        // Prevent recyclable view holders from being recreated and trigger a full rebind
        return newItem
    }
}