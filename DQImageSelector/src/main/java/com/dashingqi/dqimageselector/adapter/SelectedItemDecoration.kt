package com.dashingqi.dqimageselector.adapter

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.dashingqi.dqimageselector.R

/**
 * 选中的Item间隔布局
 * @author zhangqi
 * @since 2021/8/21
 */
class SelectedItemDecoration : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        parent.adapter?.let {
            // 获取到每一个Item的Position
            val childAdapterPosition = parent.getChildAdapterPosition(view)
            // 获取到最后一个Item所属的Position
            val lastPosition = it.itemCount.minus(1)
            val context = parent.context
            // 根据不同的position 去做不同的逻辑处理
            when (childAdapterPosition) {
                0 -> {
                    outRect.left =
                        context.resources.getDimensionPixelOffset(R.dimen.preview_selected_item_decoration_padding_10)
                }
                lastPosition -> {
                    outRect.left = context.resources.getDimensionPixelOffset(
                        R.dimen
                            .preview_selected_item_decoration_padding_16
                    )
                    outRect.right =
                        context.resources.getDimensionPixelOffset(R.dimen.preview_selected_item_decoration_padding_10)
                }
                else -> {
                    outRect.left =
                        context.resources.getDimensionPixelOffset(
                            R.dimen.preview_selected_item_decoration_padding_16
                        )
                }
            }
        }
    }
}