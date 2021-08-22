package com.dashingqi.dqimageselector.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dashingqi.dqimageselector.databinding.ItemPreviewSelectedImgBinding
import com.dashingqi.dqimageselector.diff.DiffCallback
import com.dashingqi.dqimageselector.diff.DiffEnum
import com.dashingqi.dqimageselector.listeenr.IPreviewSelectedItemListener
import com.dashingqi.dqimageselector.model.PhotoItemModel
import com.dashingqi.dqimageselector.utils.VersionUtil

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

    /**
     * Item点击的事件
     */
    private var mPreSelectedItemClickListener: IPreviewSelectedItemListener? = null

    /**
     * Item点击事件的具体实现
     */
    private var mItemClickListener: View.OnClickListener? = null

    init {
        // 点击事件的具体实现
        mItemClickListener = View.OnClickListener {
            val viewHolder = it.tag as PreviewSelectedViewHolder
            val position = viewHolder.adapterPosition
            if (position < mData.size) {
                mPreSelectedItemClickListener?.onPreSelectedItemClick(position, mData[position])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PreviewSelectedViewHolder {
        val binding = ItemPreviewSelectedImgBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val viewHolder = PreviewSelectedViewHolder(binding)
        viewHolder.itemView.tag = viewHolder
        viewHolder.itemView.setOnClickListener(mItemClickListener)
        return viewHolder
    }

    /**
     * 更新数据
     * @param holder PreviewSelectedViewHolder
     * @param position Int
     */
    override fun onBindViewHolder(holder: PreviewSelectedViewHolder, position: Int) {
        mData.takeIf { mData.size > position }?.apply {
            val photoItemModel = this[position]
            Glide.with(holder.itemView)
                .load(if (VersionUtil.isAndroidQ()) photoItemModel.uri else photoItemModel.path)
                .into(holder.binding.selectedImg)
            handleSelectedLabelVisibly(holder, photoItemModel)
        }
    }

    override fun onBindViewHolder(holder: PreviewSelectedViewHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads.isNullOrEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            when (payloads[0] as Int) {
                // 刷新Label
                NOTIFY_REFRESH_SELECT_LABEL_CODE -> {
                    mData.takeIf { mData.size > position }?.apply {
                        val photoItemModel = this[position]
                        photoItemModel.isPreSelected = !photoItemModel.isPreSelected
                        handleSelectedLabelVisibly(holder, photoItemModel)
                    }
                }
            }
        }
    }

    /**
     * 控制选中标签的显示
     */
    private fun handleSelectedLabelVisibly(holder: PreviewSelectedViewHolder, model: PhotoItemModel) {
        holder.binding.selectedLabel.visibility =
            if (model.isPreSelected) View.VISIBLE else View.GONE
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
     * 设置Item点击事件
     */
    fun setItemClickListener(itemClickListener: IPreviewSelectedItemListener) {
        mPreSelectedItemClickListener = itemClickListener
    }

    companion object {
        /** 刷新选中状态的Code */
        const val NOTIFY_REFRESH_SELECT_LABEL_CODE = 1000
    }

    /**
     * ViewHolder
     * @property binding ItemPreviewSelectedImgBinding --> Item布局对应生成的ViewBinding类
     * @constructor
     */
    inner class PreviewSelectedViewHolder(var binding: ItemPreviewSelectedImgBinding) :
        RecyclerView.ViewHolder(binding.root)
}