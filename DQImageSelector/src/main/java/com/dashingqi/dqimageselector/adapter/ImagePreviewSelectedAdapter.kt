package com.dashingqi.dqimageselector.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dashingqi.dqimageselector.databinding.ItemPreviewSelectedImgBinding
import com.dashingqi.dqimageselector.diff.DiffCallback
import com.dashingqi.dqimageselector.diff.DiffEnum
import com.dashingqi.dqimageselector.model.PhotoItemModel

/**
 * 预览界面中，选中图片展示的适配器
 * @author zhangqi61
 * @since 2021/8/21
 */
class ImagePreviewSelectedAdapter : RecyclerView.Adapter<ImagePreviewSelectedAdapter.PreviewSelectedViewHolder>() {

    /**
     * 数据源
     */
    private var mData: MutableList<PhotoItemModel> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PreviewSelectedViewHolder {
        val binding = ItemPreviewSelectedImgBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PreviewSelectedViewHolder(binding)
    }

    /**
     * 更新数据
     * @param holder PreviewSelectedViewHolder
     * @param position Int
     */
    override fun onBindViewHolder(holder: PreviewSelectedViewHolder, position: Int) {
        mData.takeIf { mData.size > position }?.apply {
            Glide.with(holder.itemView).load(this[position].path).into(holder.binding.selectedImg)
        }
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    /**
     * 设置数据，更新列表
     * @param data MutableList<PhotoItemModel> 数据源
     */
    fun setData(data: MutableList<PhotoItemModel>) {
        // 使用DiffUtil替代notify
        val calculateDiff = DiffUtil.calculateDiff(
            DiffCallback(
                mData,
                data,
                DiffEnum.IMAGE_SELECTOR_UPDATE_SELECTED_RV
            ), true
        )
        calculateDiff.dispatchUpdatesTo(this)
        mData = data
    }

    /**
     * ViewHolder
     * @property binding ItemPreviewSelectedImgBinding --> Item布局对应生成的ViewBinding类
     * @constructor
     */
    inner class PreviewSelectedViewHolder(var binding: ItemPreviewSelectedImgBinding) :
        RecyclerView.ViewHolder(binding.root)
}