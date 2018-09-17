package com.nerdery.rtaza.mvvmdemo.ui.jobs

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.nerdery.rtaza.mvvmdemo.R
import com.nerdery.rtaza.mvvmdemo.ui.core.BaseActivity
import com.nerdery.rtaza.mvvmdemo.ui.util.observeNonNull
import com.nerdery.rtaza.mvvmdemo.ui.view.OffsetItemDecoration
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_jobs.*
import javax.inject.Inject

class JobsActivity : BaseActivity() {
    private lateinit var viewModel: JobsViewModel
    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    @Inject lateinit var listAdapter: JobsListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jobs)

        recyclerView.apply {
            addItemDecoration(
                OffsetItemDecoration(
                    topOffset = resources.getDimensionPixelSize(R.dimen.spacing_small),
                    bottomOffset = resources.getDimensionPixelSize(R.dimen.spacing_small),
                    leftOffset = resources.getDimensionPixelSize(R.dimen.spacing_medium),
                    rightOffset = resources.getDimensionPixelSize(R.dimen.spacing_medium)
                )
            )
            lifecycle.addObserver(listAdapter)
            adapter = listAdapter
            layoutManager = LinearLayoutManager(context)
        }

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(JobsViewModel::class.java)

        viewModel.loading.observeNonNull(this) { loading ->
            progressBar.visibility = if (loading && listAdapter.isEmpty()) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }

        viewModel.getPresentation().observeNonNull(this) { presentation ->
            toggleEmptyStateVisibility(false)
            listAdapter.submitList(presentation.models)
        }

        viewModel.getNoJobsFound().observeNonNull(this) {
            emptyStateImageView.setImageResource(R.drawable.icon_no_active_jobs)
            emptyStateTitleTextView.setText(R.string.empty_state_title_no_jobs)
            emptyStateDescriptionTextView.setText(R.string.empty_state_description_no_jobs)
            toggleEmptyStateVisibility(true)
        }

        viewModel.error.observeNonNull(this) { error ->
            if (listAdapter.isEmpty()) {
                emptyStateImageView.setImageResource(error.iconResourceId)
                emptyStateTitleTextView.setText(error.titleResourceId)
                emptyStateDescriptionTextView.setText(error.descriptionResourceId)
                toggleEmptyStateVisibility(true)
            }
            // Don't show an error if there're jobs in the list loaded from Room since the request was not explicitly
            // request by the user, but consider adding some sort of indication that the jobs being shown may not be the
            // freshest.
        }

        viewModel.bind()
    }

    private fun toggleEmptyStateVisibility(show: Boolean) {
        if (show) {
            recyclerView.visibility = View.INVISIBLE
            emptyStateGroup.visibility = View.VISIBLE
        } else {
            emptyStateGroup.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
        }
    }
}