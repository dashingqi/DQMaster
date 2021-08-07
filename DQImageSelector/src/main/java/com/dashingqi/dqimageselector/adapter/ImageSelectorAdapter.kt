package com.dashingqi.dqimageselector.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dashingqi.dqimageselector.R
import com.dashingqi.dqimageselector.model.PhotoItemModel

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImgSelectorViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_image, parent, false)
        return ImgSelectorViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImgSelectorViewHolder, position: Int) {
        mData?.get(position)?.let {
            holder.img?.let { imageView -> Glide.with(holder.itemView).load(it.path).into(imageView) }
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
     * ViewHolder
     */
    class ImgSelectorViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var img: ImageView? = null

        init {
            img = view.findViewById(R.id.image)
        }
    }
}