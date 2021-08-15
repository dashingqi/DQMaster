package com.dashingqi.dqimageselector.adapter

import android.graphics.Canvas
import android.graphics.Rect
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * 图片选择器的分割线
 * @author zhangqi61
 * @since 2021/8/14
 */
class SelectorItemDecoration(var lintCount: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        // 获取到子View所存在的位置
        val childAdapterPosition = parent.getChildAdapterPosition(view)
        // 子View的个数
        val itemCount = parent.adapter?.itemCount ?: 0
        outRect.left = MARGIN_OFFSET
        outRect.top = MARGIN_OFFSET
        // 设置右边距
        if ((childAdapterPosition + 1) % lintCount == 0) {
            Log.d(TAG, "childAdapterPosition == $childAdapterPosition  ")
            outRect.right = MARGIN_OFFSET
        }

        // 设置底边距
        if ((childAdapterPosition + 1) > (itemCount.minus(4))) {
            outRect.bottom = MARGIN_OFFSET
        }
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)
        parent.adapter?.let {
            val itemCount = it.itemCount
            for (index in 0 until itemCount) {
                if (index % (lintCount - 1) == 0) {

                }
            }
        }
    }

    companion object {
        /**
         * 间隔
         */
        const val MARGIN_OFFSET = 4

        /**
         * TAG
         */
        const val TAG = "SelectorItemDecoration"
    }
}