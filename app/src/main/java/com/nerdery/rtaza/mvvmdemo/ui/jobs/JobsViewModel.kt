package com.nerdery.rtaza.mvvmdemo.ui.jobs

import android.app.Application
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.content.Context
import android.support.annotation.DrawableRes
import android.support.annotation.StringRes
import com.nerdery.rtaza.mvvmdemo.R
import com.nerdery.rtaza.mvvmdemo.data.core.Resource
import com.nerdery.rtaza.mvvmdemo.data.local.model.JobStatus
import com.nerdery.rtaza.mvvmdemo.data.local.model.JobWithRelations
import com.nerdery.rtaza.mvvmdemo.data.repository.JobRepository
import com.nerdery.rtaza.mvvmdemo.ui.core.BaseViewModel
import com.nerdery.rtaza.mvvmdemo.ui.core.SingleLiveData
import com.nerdery.rtaza.mvvmdemo.ui.util.JobIconUtil
import com.nerdery.rtaza.mvvmdemo.ui.util.TextFormatter
import com.nerdery.rtaza.mvvmdemo.ui.util.Visibility
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class JobsViewModel @Inject constructor(
    application: Application,
    private val jobRepository: JobRepository
) : BaseViewModel(application) {
    @Visibility val loading: SingleLiveData<Boolean> = SingleLiveData()
    @StringRes val error: SingleLiveData<Int> = SingleLiveData()
    private val presentation: MutableLiveData<Presentation> = MutableLiveData()

    fun presentation(): LiveData<Presentation> {
        return presentation
    }

    fun bind() {
        compositeDisposable.add(
            jobRepository.getJobs(true)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { resource: Resource<List<JobWithRelations>> ->
                    when (resource) {
                        is Resource.Loading -> {
                            if (resource.data != null) {
                                presentation.value = Presentation(getApplication(), resource.data)
                            }
                            loading.value = true
                        }
                        is Resource.Success -> {
                            presentation.value = Presentation(getApplication(), resource.data)
                            loading.value = false
                        }
                        is Resource.Error -> {
                            error.value = resource.error?.messageResourceId
                            loading.value = false
                        }
                    }
                })
    }

    data class Presentation(
        private val context: Context,
        private val jobsWithRelations: List<JobWithRelations>?
    ) {
        val models: MutableList<Model> = ArrayList()

        init {
            jobsWithRelations?.forEach { jobWithRelations ->
                with(jobWithRelations.job) {
                    models.add(
                        Model(
                            id,
                            getStatus(),
                            getStatus().label,
                            task.label,
                            JobIconUtil.getTaskIconId(task),
                            location.street,
                            context.getString(R.string.format_city_state, location.city, location.state),
                            TextFormatter.formatFullName(
                                context,
                                jobWithRelations.worker?.firstName,
                                jobWithRelations.worker?.lastName
                            ),
                            eta
                        )
                    )
                }
            }
        }

        data class Model(
            val jobId: Long,
            val jobStatus: JobStatus,
            @StringRes val jobStatusTextId: Int,
            @StringRes val jobTaskTextId: Int,
            @DrawableRes val jobTaskIconId: Int,
            val addressLine1Text: String,
            val addressLine2Text: String,
            val workerNameText: String,
            val eta: Long
        )
    }
}