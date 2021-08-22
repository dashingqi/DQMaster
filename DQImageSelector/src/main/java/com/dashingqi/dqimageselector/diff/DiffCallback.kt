package com.dashingqi.dqimageselector.diff

import androidx.recyclerview.widget.DiffUtil
import com.dashingqi.dqimageselector.model.PhotoItemModel

/**
 * DiffUtil的条件类
 * @author zhangqi61
 * @since 2021/8/19
 */
class DiffCallback(
    /** 老的数据集*/
    private val oldData: MutableList<PhotoItemModel>,
    /** 新的数据集*/
    private val newData: MutableList<PhotoItemModel>,
    /** 不同类别用于判断Item上的内容是否相同*/
    private val diffEnum: DiffEnum? = null
) : DiffUtil.Callback() {
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
        return when (diffEnum) {
            DiffEnum.IMAGE_SELECTOR_UPDATE_NUMBER -> {
                oldData[oldItemPosition].selectNumber == newData[newItemPosition].selectNumber
            }
            DiffEnum.IMAGE_SELECTOR_UPDATE_SELECTED_RV,
            DiffEnum.IMAGE_SELECTOR_UPDATE_SELECTOR_RV -> {
                oldData[oldItemPosition].path == newData[newItemPosition].path
            }
            DiffEnum.IMAGE_SELECTOR_UPDATE_SELECTED_LABEL -> {
                oldData[oldItemPosition].isPreSelected == newData[newItemPosition].isPreSelected
            }
            else -> {
                false
            }
        }
    }
}