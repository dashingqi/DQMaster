package com.dashingqi.dqimageselector.adapter

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.dashingqi.dqimageselector.R

/**
 * 图片选择器的分割线
 * @author zhangqi61
 * @since 2021/8/14
 */
class SelectorItemDecoration() : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.top = parent.context.resources.getDimensionPixelOffset(R.dimen.selector_grid_item_spacing)
    }

    companion object {

        /**
         * TAG
         */
        const val TAG = "SelectorItemDecoration"
    }
}