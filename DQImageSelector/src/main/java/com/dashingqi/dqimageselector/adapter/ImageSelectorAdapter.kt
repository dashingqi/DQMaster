package com.dashingqi.dqimageselector.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dashingqi.dqimageselector.R
import com.dashingqi.dqimageselector.listeenr.IPhotoItemListener
import com.dashingqi.dqimageselector.model.PhotoItemModel
import kotlin.math.log

/**
 * @author : zhangqi
 * @time : 2021/8/7
 * desc :
 */
class ImageSelectorAdapter : RecyclerView.Adapter<ImageSelectorAdapter.ImgSelectorViewHolder>() {

    /**
     * 数据源
     */
    private var mData: MutableList<PhotoItemModel>? = null

    /**
     * item的点击事件
     */
    private var mPhotoItemClickListener: IPhotoItemListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImgSelectorViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_image, parent, false)
        return ImgSelectorViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImgSelectorViewHolder, position: Int) {
        mData?.get(position)?.let { data ->
            holder.img?.let { imageView -> Glide.with(holder.itemView).load(data.path).into(imageView) }
            holder.tvNumber?.isSelected = data.isSelected
            holder.itemView.setOnClickListener {
                mPhotoItemClickListener?.onItemClick(position, data)
            }
            holder.tvNumber?.let {
                it.setOnClickListener {
                    mPhotoItemClickListener?.onSelectClick(position, data)
                }
            }
        }
    }

    override fun onBindViewHolder(holder: ImgSelectorViewHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads.isNullOrEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            Log.d(TAG, "size is ${payloads.size}")
            var code = payloads[0] as Int
            when (code) {
                NOTIFY_REFRESH_SELECT_CODE -> {
                    mData?.get(position)?.let {
                        it.isSelected = !it.isSelected
                        holder.tvNumber?.isSelected = it.isSelected
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return mData?.size ?: 0
    }

    /**
     * 设置数据源
     */
    fun setData(data: MutableList<PhotoItemModel>) {
        mData = data
        notifyDataSetChanged()
    }

    /**
     * 设置点击事件
     */
    fun setItemListener(itemClickListener: IPhotoItemListener) {
        mPhotoItemClickListener = itemClickListener
    }

    companion object {

        /** 刷新选中状态的Code */
        const val NOTIFY_REFRESH_SELECT_CODE = 1000

        /** TAG */
        const val TAG = "ImageSelectorAdapter"
    }


    /**
     * ViewHolder
     */
    class ImgSelectorViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var img: ImageView? = null
        var tvNumber: TextView? = null

        init {
            img = view.findViewById(R.id.image)
            tvNumber = view.findViewById(R.id.number)
        }
    }
}