package com.nerdery.rtaza.mvvmdemo.ui.core

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import com.nerdery.rtaza.mvvmdemo.R
import com.nerdery.rtaza.mvvmdemo.log.LogUtil
import timber.log.Timber

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        Timber.tag(LogUtil.getLogTag(this))
        Timber.v("onCreate")
        super.onCreate(savedInstanceState)
    }

    override fun setContentView(layoutResID: Int) {
        super.setContentView(layoutResID)
        val toolbar: Toolbar? = findViewById(R.id.action_bar)
        if (toolbar != null) {
            initializeAppBar(toolbar)
        }
    }

    private fun initializeAppBar(toolbar: Toolbar) {
        setSupportActionBar(toolbar)

        if (parentActivityIntent != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun onStart() {
        Timber.tag(LogUtil.getLogTag(this))
        Timber.v("onStart")
        super.onStart()
    }

    override fun onResume() {
        Timber.tag(LogUtil.getLogTag(this))
        Timber.v("onResume")
        super.onResume()
    }

    override fun onPause() {
        Timber.tag(LogUtil.getLogTag(this))
        Timber.v("onPause")
        super.onPause()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        Timber.tag(LogUtil.getLogTag(this))
        Timber.v("onSaveInstanceState")
        super.onSaveInstanceState(outState)
    }

    override fun onStop() {
        Timber.tag(LogUtil.getLogTag(this))
        Timber.v("onStop")
        super.onStop()
    }

    override fun onDestroy() {
        Timber.tag(LogUtil.getLogTag(this))
        Timber.v("onDestroy")
        super.onDestroy()
    }
}