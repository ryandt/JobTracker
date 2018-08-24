package com.nerdery.rtaza.mvvmdemo.ui.view

import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View

/**
 * [RecyclerView.ItemDecoration] responsible for adding top, bottom, left, and right offsets to each item view of a
 * [RecyclerView].
 */
class OffsetItemDecoration(
    private val topOffset: Int = 0,
    private val bottomOffset: Int = 0,
    private val leftOffset: Int = 0,
    private val rightOffset: Int = 0
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        offsetsRect: Rect,
        itemView: View,
        recyclerView: RecyclerView,
        state: RecyclerView.State
    ) {
        with(offsetsRect) {
            if (recyclerView.getChildAdapterPosition(itemView) == 0) {
                top = topOffset
            }
            bottom = bottomOffset
            left = leftOffset
            right = rightOffset
        }
    }
}