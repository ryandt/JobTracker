package com.nerdery.rtaza.jobtracker.ui.util

import android.content.Context
import android.support.annotation.AttrRes
import android.util.TypedValue

/**
 * Retrieve the value of an attribute in the Theme.
 */
fun Context.resolveThemeAttribute(@AttrRes resourceId: Int): Int {
    val typedValue = TypedValue()
    theme.resolveAttribute(resourceId, typedValue, true)
    return typedValue.data
}