package com.dashingqi.dqimageselector.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dashingqi.dqimageselector.databinding.ItemPreviewAllImgBinding
import com.dashingqi.dqimageselector.diff.DiffCallback
import com.dashingqi.dqimageselector.diff.DiffEnum
import com.dashingqi.dqimageselector.model.PhotoItemModel
import com.dashingqi.dqimageselector.utils.VersionUtil

/**
 * 用于展示所有图片的适配器
 * @author zhangqi
 * @since 2021/8/21
 */
class ImagePreviewAllAdapter : RecyclerView.Adapter<ImagePreviewAllAdapter.PreviewAllViewHolder>() {
    /**
     * 数据源
     */
    private var mData: MutableList<PhotoItemModel> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PreviewAllViewHolder {
        val binding = ItemPreviewAllImgBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PreviewAllViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PreviewAllViewHolder, position: Int) {

        Glide.with(holder.itemView)
            .load(if (VersionUtil.isAndroidQ()) mData[position].uri else mData[position].path)
            .into(holder.binding.previewAll)
    }

    /**
     * 设置数据源，更新列表
     * @param data MutableList<PhotoItemModel>
     */
    fun setData(data: MutableList<PhotoItemModel>) {
        val diffCallback =
            DiffUtil.calculateDiff(DiffCallback(mData, data, DiffEnum.IMAGE_SELECTOR_UPDATE_SELECTOR_RV), true)
        diffCallback.dispatchUpdatesTo(this)
        mData = data
    }

    /**
     * 返回Item数量
     * @return Int
     */
    override fun getItemCount(): Int {
        return mData.size
    }

    inner class PreviewAllViewHolder(var binding: ItemPreviewAllImgBinding) : RecyclerView.ViewHolder(binding.root)
}