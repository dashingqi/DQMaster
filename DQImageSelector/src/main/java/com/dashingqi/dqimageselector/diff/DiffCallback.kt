package com.dashingqi.dqimageselector.diff

import androidx.recyclerview.widget.DiffUtil
import com.dashingqi.dqimageselector.model.PhotoItemModel

/**
 * @author zhangqi61
 * @since 2021/8/19
 */
class DiffCallback(private val oldData: MutableList<PhotoItemModel>, private val newData: MutableList<PhotoItemModel>) :
    DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldData.size
    }

    override fun getNewListSize(): Int {
        return newData.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldData[oldItemPosition].id == newData[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldData[oldItemPosition].selectNumber == newData[newItemPosition].selectNumber
    }
}