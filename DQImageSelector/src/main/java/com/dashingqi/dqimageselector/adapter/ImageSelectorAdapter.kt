package com.dashingqi.dqimageselector.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.Group
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dashingqi.dqimageselector.R
import com.dashingqi.dqimageselector.listeenr.IPhotoItemListener
import com.dashingqi.dqimageselector.model.ConfigData
import com.dashingqi.dqimageselector.model.PhotoItemModel
import kotlin.collections.ArrayList

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
     * 配置数据
     */
    private var mConfigData: ConfigData? = null

    /**
     * item的点击事件
     */
    private var mPhotoItemClickListener: IPhotoItemListener? = null

    /**
     * 用于存储选择的条目数据
     */
    private val mSelectedItems: ArrayList<PhotoItemModel> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImgSelectorViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_image, parent, false)
        return ImgSelectorViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImgSelectorViewHolder, position: Int) {
        mData?.get(position)?.let { data ->
            holder.img?.let { imageView -> Glide.with(holder.itemView).load(data.path).into(imageView) }
            holder.ivSelect?.isSelected = data.isSelected
            holder.mCountGroup?.visibility = if (data.isSelected) View.VISIBLE else View.INVISIBLE
            holder.tvNumber?.text = "${data.selectNumber}"
            holder.itemView.setOnClickListener {
                mPhotoItemClickListener?.onItemClick(position, data)
            }
            holder.ivSelect?.let {
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
                        when {
                            // 之前状态是选中的
                            it.isSelected -> {
                                it.isSelected = !it.isSelected
                                holder.ivSelect?.isSelected = false
                                holder.mCountGroup?.visibility = View.INVISIBLE
                                val tempSelectItems = mSelectedItems
                                if (handleSelect(it.isSelected, it)) {
                                    orderNumber(holder, false, tempSelectItems, it)
                                }
                            }
                            // 之前状态是未选中的
                            !it.isSelected && isCanSelect() -> {
                                it.isSelected = !it.isSelected
                                holder.ivSelect?.isSelected = true
                                holder.mCountGroup?.visibility = View.VISIBLE
                                if (handleSelect(it.isSelected, it)) {
                                    orderNumber(holder, true)
                                }
                            }
                            !it.isSelected && !isCanSelect() -> {
                                // 不能在选择了
                                Toast.makeText(holder.itemView.context, "不能在选择了!", Toast.LENGTH_LONG).show()
                            }
                        }

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

    /**
     * 设置配置数据
     */
    fun setConfigData(configData: ConfigData?) {
        mConfigData = configData
    }

    /**
     * 用于判断是否能够选中
     */
    private fun isCanSelect(): Boolean {
        return mSelectedItems.size < mConfigData?.maxSelectSize ?: 9
    }

    /**
     * 处理选择的业务
     */
    private fun handleSelect(isSelect: Boolean, item: PhotoItemModel): Boolean {
        var handleResult = false
        when (isSelect) {
            true -> {
                if (!mSelectedItems.contains(item)) {
                    mSelectedItems.add(item)
                    handleResult = true
                }
            }
            false -> {
                if (mSelectedItems.contains(item)) {
                    mSelectedItems.remove(item)
                    handleResult = true
                }
            }
        }
        return handleResult
    }

    /**
     * 排序选中的序号
     */
    private fun orderNumber(
        holder: ImgSelectorViewHolder,
        isSelected: Boolean,
        selectIds: ArrayList<PhotoItemModel>? = null,
        currentItem: PhotoItemModel? = null
    ) {
        when (isSelected) {
            true -> {
                holder.tvNumber?.apply {
                    currentItem?.selectNumber = mSelectedItems.size
                    text = "${mSelectedItems.size}"
                }
            }
            false -> {
                // 当前由选中变成未选中 需要刷新选中的数目,需要把之后选中的序号进行减一的操作

                selectIds?.takeIf { currentItem != null }?.apply {
                    var indexOf = selectIds.indexOf(currentItem)

                    if (indexOf >= 0) {
                        for (index in indexOf until size) {
                            this[index].selectNumber = --this[index].selectNumber
                        }
                    }
                    //notifyDataSetChanged()
                }
            }
        }
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
        /** 图片*/
        var img: ImageView? = null

        /** 选中*/
        var ivSelect: ImageView? = null

        /** 选中的蒙层*/
        var selectView: View? = null

        /** 选中的序号*/
        var tvNumber: TextView? = null

        /** group */
        var mCountGroup: Group? = null

        init {
            img = view.findViewById(R.id.image)
            ivSelect = view.findViewById(R.id.ivSelect)
            selectView = view.findViewById(R.id.selectView)
            tvNumber = view.findViewById(R.id.tvNumber)
            mCountGroup = view.findViewById(R.id.countGroup)
        }
    }
}