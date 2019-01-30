package com.nerdery.rtaza.jobtracker.ui.jobs

import android.app.Application
import android.content.Context
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nerdery.rtaza.jobtracker.R
import com.nerdery.rtaza.jobtracker.data.core.Error
import com.nerdery.rtaza.jobtracker.data.core.Resource
import com.nerdery.rtaza.jobtracker.data.local.model.JobStatus
import com.nerdery.rtaza.jobtracker.data.local.model.JobWithRelations
import com.nerdery.rtaza.jobtracker.data.repository.JobRepository
import com.nerdery.rtaza.jobtracker.ui.core.BaseViewModel
import com.nerdery.rtaza.jobtracker.ui.core.SingleLiveEvent
import com.nerdery.rtaza.jobtracker.ui.util.JobIconUtil
import com.nerdery.rtaza.jobtracker.ui.util.TextFormatter
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class JobsViewModel @Inject constructor(
    application: Application,
    private val jobRepository: JobRepository
) : BaseViewModel(application) {
    val loading: SingleLiveEvent<Boolean> = SingleLiveEvent()
    val error: SingleLiveEvent<Error> = SingleLiveEvent()
    private val presentation: MutableLiveData<Presentation> = MutableLiveData()
    private val noJobsFound: MutableLiveData<Unit> = MutableLiveData()

    fun getPresentation(): LiveData<Presentation> {
        return presentation
    }

    fun getNoJobsFound(): LiveData<Unit> {
        return noJobsFound
    }

    fun bind() {
        jobRepository.getJobs(true)
            .subscribeOn(Schedulers.io())
            .subscribe { resource: Resource<List<JobWithRelations>> ->
                when (resource) {
                    is Resource.Loading -> {
                        if (resource.data != null) {
                            presentation.postValue(Presentation(getApplication(), resource.data))
                        }
                        loading.postValue(true)
                    }
                    is Resource.ResourceFound -> {
                        presentation.postValue(Presentation(getApplication(), resource.data!!))
                        loading.postValue(false)
                    }
                    is Resource.NoResourceFound -> {
                        noJobsFound.postValue(Unit)
                        loading.postValue(false)
                    }
                    is Resource.Error -> {
                        error.postValue(resource.error)
                        loading.postValue(false)
                    }
                }
            }.addTo(compositeDisposable)
    }

    data class Presentation(
        private val context: Context,
        private val jobsWithRelations: List<JobWithRelations>
    ) {
        val models: MutableList<Model> = ArrayList(jobsWithRelations.size)

        init {
            jobsWithRelations.forEach { jobWithRelations ->
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