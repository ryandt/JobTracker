package com.nerdery.rtaza.jobtracker.ui.util

import android.content.res.ColorStateList
import android.view.View
import androidx.annotation.ColorInt
import androidx.core.view.ViewCompat

fun View.setBackgroundTint(@ColorInt color: Int) =
    ViewCompat.setBackgroundTintList(this, ColorStateList.valueOf(color))