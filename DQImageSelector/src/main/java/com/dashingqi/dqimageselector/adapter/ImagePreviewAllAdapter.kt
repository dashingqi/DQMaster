package com.dashingqi.dqimageselector.adapter

import android.app.Activity
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dashingqi.dqimageselector.R
import com.dashingqi.dqimageselector.databinding.ItemPreviewAllImgBinding
import com.dashingqi.dqimageselector.diff.DiffCallback
import com.dashingqi.dqimageselector.diff.DiffEnum
import com.dashingqi.dqimageselector.model.PhotoItemModel
import com.dashingqi.dqimageselector.selection.SelectionIns
import com.dashingqi.dqimageselector.utils.MediaStoreUtil
import com.dashingqi.dqimageselector.utils.VersionUtil

/**
 * 用于展示所有图片的适配器
 * @author zhangqi
 * @since 2021/8/21
 */
class ImagePreviewAllAdapter(var activity: Activity) :
    RecyclerView.Adapter<ImagePreviewAllAdapter.PreviewAllViewHolder>() {

    /** PlaceHolder Drawable*/
    private var mPlaceHolder: Drawable? = null

    init {
//        val obtain =
//            activity.theme.obtainStyledAttributes(IntArray(R.attr.item_placeHolder))
//        mPlaceHolder = obtain.getDrawable(0)
//        obtain.recycle()
    }

    /**
     * 数据源
     */
    private var mData: MutableList<PhotoItemModel> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PreviewAllViewHolder {
        val binding = ItemPreviewAllImgBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PreviewAllViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PreviewAllViewHolder, position: Int) {
        mData.takeIf { position < mData.size - 1 }?.apply {
            this[position].uri?.let {
                val bitmapSize = MediaStoreUtil.getBitmapSize(it, activity)
                SelectionIns.mEngine.loadImage(
                    holder.itemView.context, holder.binding.previewAll, it,
                    bitmapSize.x, bitmapSize.y, mPlaceHolder
                )
            }
        }
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