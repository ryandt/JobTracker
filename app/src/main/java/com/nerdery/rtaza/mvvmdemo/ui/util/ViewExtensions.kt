package com.nerdery.rtaza.mvvmdemo.ui.util

import android.content.res.ColorStateList
import android.support.annotation.ColorInt
import android.support.v4.view.ViewCompat
import android.view.View

fun View.setBackgroundTint(@ColorInt color: Int) =
    ViewCompat.setBackgroundTintList(this, ColorStateList.valueOf(color))