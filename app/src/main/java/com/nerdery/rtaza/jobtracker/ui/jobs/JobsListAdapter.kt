package com.nerdery.rtaza.jobtracker.ui.jobs

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil
import android.view.LayoutInflater
import android.view.ViewGroup
import com.nerdery.rtaza.jobtracker.R
import com.nerdery.rtaza.jobtracker.ui.util.isNullOrEmpty
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit

class JobsListAdapter : ListAdapter<JobsViewModel.Presentation.Model, JobViewHolder>(diffUtilItemCallback),
    LifecycleObserver {
    private var dataSet: List<JobsViewModel.Presentation.Model>? = null
    private val etaUpdateInterval = Pair<Long, TimeUnit>(1, TimeUnit.MINUTES)
    private var etaUpdateObservable: Observable<Long>
    private var etaUpdateDisposable: Disposable? = null

    init {
        setHasStableIds(true)
        etaUpdateObservable = Observable.interval(etaUpdateInterval.first, etaUpdateInterval.second)
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun submitList(list: List<JobsViewModel.Presentation.Model>?) {
        // Reset the ETA update observable anytime a new list is submitted
        etaUpdateDisposable?.dispose()
        dataSet = list
        super.submitList(list)
        startEtaUpdateInterval()
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
                    is EtaUpdatePayload -> holder.updateEta()
                }
            }
        }
    }

    override fun getItemId(position: Int): Long {
        return getItem(position).jobId
    }

    fun isEmpty() = dataSet.isNullOrEmpty()

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun startEtaUpdateInterval() {
        if (!isEmpty()) {
            etaUpdateDisposable = etaUpdateObservable.subscribe {
                updateEtas()
            }
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun clearDisposables() {
        etaUpdateDisposable?.dispose()
    }

    private fun updateEtas() {
        notifyItemRangeChanged(0, itemCount, EtaUpdatePayload())
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

private class EtaUpdatePayload