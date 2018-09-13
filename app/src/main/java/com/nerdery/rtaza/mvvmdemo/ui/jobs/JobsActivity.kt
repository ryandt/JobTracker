package com.nerdery.rtaza.mvvmdemo.ui.jobs

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
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
            progressBar.visibility = if (loading && listAdapter.itemCount == 0) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }
        viewModel.error.observeNonNull(this) { error ->
            Toast.makeText(this, error, Toast.LENGTH_LONG).show()
        }
        viewModel.getPresentation().observeNonNull(this) { presentation ->
            if (presentation.models.isEmpty()) {
                recyclerView.visibility = View.INVISIBLE
                emptyStateGroup.visibility = View.VISIBLE
            } else {
                emptyStateGroup.visibility = View.GONE
                recyclerView.visibility = View.VISIBLE
                listAdapter.submitList(presentation.models)
            }
        }

        viewModel.bind()
    }
}