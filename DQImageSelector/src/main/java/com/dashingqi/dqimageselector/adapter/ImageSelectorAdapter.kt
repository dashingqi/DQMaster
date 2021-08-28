package com.dashingqi.dqimageselector.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dashingqi.dqimageselector.R
import com.dashingqi.dqimageselector.databinding.ItemImageBinding
import com.dashingqi.dqimageselector.diff.DiffCallback
import com.dashingqi.dqimageselector.diff.DiffEnum
import com.dashingqi.dqimageselector.listeenr.IPhotoItemListener
import com.dashingqi.dqimageselector.model.PhotoItemModel
import com.dashingqi.dqimageselector.selection.SelectionIns
import com.dashingqi.dqimageselector.utils.VersionUtil
import kotlin.collections.ArrayList

/**
 * 图片选择器的适配器
 * @author : zhangqi
 * @time : 2021/8/7
 * desc :
 */
class ImageSelectorAdapter(var recyclerView: RecyclerView): RecyclerView.Adapter<ImageSelectorAdapter.ImgSelectorViewHolder>() {

    /**
     * 数据源
     */
    var mData: ArrayList<PhotoItemModel> = ArrayList()

    /**
     * item的点击事件
     */
    private var mPhotoItemClickListener: IPhotoItemListener? = null

    /**
     * 用于存储选择的条目数据
     */
    val mSelectedItems: ArrayList<PhotoItemModel> = ArrayList()

    /**
     * ItemView的点击事件
     */
    private var mOnItemClickListener: View.OnClickListener? = null

    /**
     * IvSelect 的点击事件
     */
    private var mOnSelectClickListener: View.OnClickListener? = null

    /** 计算后的图片大小*/
    private var mImageResize: Int = 0

    init {

        mOnItemClickListener = View.OnClickListener {
            val viewHolder = it.tag as ImgSelectorViewHolder
            val position = viewHolder.adapterPosition
            mPhotoItemClickListener?.onItemClick(position, mData[position])
        }

        mOnSelectClickListener = View.OnClickListener {
            val viewHolder = it.tag as ImgSelectorViewHolder
            val position = viewHolder.adapterPosition
            mPhotoItemClickListener?.onSelectClick(position, mData[position])
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImgSelectorViewHolder {
        Log.d(TAG, "onCreateViewHolder")
        val itemBinding = ItemImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val viewHolder = ImgSelectorViewHolder(itemBinding)
        viewHolder.itemView.tag = viewHolder
        viewHolder.binding.ivSelect.tag = viewHolder
        viewHolder.itemView.setOnClickListener(mOnItemClickListener)
        viewHolder.binding.ivSelect.setOnClickListener(mOnSelectClickListener)
        return viewHolder
    }

    /**
     * onBindViewHolder 仅仅做针对UI更新的动作，比如 setText()等 对View的操作
     *
     */
    override fun onBindViewHolder(holder: ImgSelectorViewHolder, position: Int) {
        Log.d(TAG, "onBindViewHolder --> position size =  ${mData.size}")
        mData.takeIf { position < mData.size - 1 }?.apply {
            this[position].let { data ->
                // 适配Android Q
//                Glide.with(holder.itemView).load(if (VersionUtil.isAndroidQ()) data.uri else data.path).into(
//                    holder.binding.image
//                )
                SelectionIns.mEngine.loadCropImage(
                    holder.itemView.context, holder.binding.image, data.uri!!,
                    getImageResize(holder.itemView.context)
                )

                holder.binding.ivSelect.isSelected = data.isSelected
                holder.binding.countGroup.visibility = if (data.isSelected) View.VISIBLE else View.INVISIBLE
                holder.binding.tvNumber.text = "${data.selectNumber}"
            }
        }

    }

    override fun onBindViewHolder(holder: ImgSelectorViewHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads.isNullOrEmpty()) {
            Log.d(TAG, "onBindViewHolder --> isNullOrEmpty ")
            super.onBindViewHolder(holder, position, payloads)
        } else {
            Log.d(TAG, "size is ${payloads.size}")
            when (payloads[0] as Int) {
                NOTIFY_REFRESH_SELECT_CODE -> {
                    mData[position].let {
                        when {
                            // 之前状态是选中的
                            it.isSelected -> {
                                it.isSelected = !it.isSelected
                                holder.binding.ivSelect.isSelected = false
                                val preSelectItems = mutableListOf<PhotoItemModel>()
                                preSelectItems.addAll(mSelectedItems)
                                Log.d(TAG, "onBindViewHolder: preSize = ${preSelectItems.size}")
                                if (handleSelect(it.isSelected, it)) {
                                    mPhotoItemClickListener?.updateEditView()
                                    holder.binding.countGroup.visibility = View.INVISIBLE
                                    val oldData = mutableListOf<PhotoItemModel>()
                                    mData.forEach { data ->
                                        oldData.add(
                                            PhotoItemModel(
                                                data.id, data.path, data.name, data.date, data.isSelected, data
                                                    .selectNumber, data.uri
                                            )
                                        )
                                    }
                                    Log.d(TAG, "onBindViewHolder: preSize = ${preSelectItems.size}")
                                    orderNumber(holder, false, preSelectItems, it, oldData)
                                }
                            }
                            // 之前状态是未选中的
                            !it.isSelected && isCanSelect() -> {
                                it.isSelected = !it.isSelected
                                holder.binding.ivSelect.isSelected = true
                                if (handleSelect(it.isSelected, it)) {
                                    mPhotoItemClickListener?.updateEditView()
                                    holder.binding.countGroup.visibility = View.VISIBLE
                                    it.selectNumber = mSelectedItems.size
                                    orderNumber(holder, true, null, it)
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

    /**
     * 返回AdapterItem的size
     */
    override fun getItemCount(): Int {
        return mData.size
    }

    /**
     * 设置点击事件
     */
    fun setItemListener(itemClickListener: IPhotoItemListener) {
        mPhotoItemClickListener = itemClickListener
    }


    /**
     * 用于判断是否能够选中
     */
    private fun isCanSelect(): Boolean {
        return mSelectedItems.size < SelectionIns.mMaxSize
    }

    /**
     * 设置数据源
     */
    fun setData(data: ArrayList<PhotoItemModel>) {
        val diffResult =
            DiffUtil.calculateDiff(DiffCallback(mData, data, DiffEnum.IMAGE_SELECTOR_UPDATE_SELECTOR_RV), true)
        diffResult.dispatchUpdatesTo(this)
        mData = data
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
        preSelectIds: MutableList<PhotoItemModel>? = null,
        currentItem: PhotoItemModel? = null,
        oldData: MutableList<PhotoItemModel> = mutableListOf()
    ) {
        when (isSelected) {
            true -> {
                holder.binding.tvNumber.text = "${currentItem?.selectNumber}"
            }
            false -> {
                // 当前由选中变成未选中 需要刷新选中的数目,需要把之后选中的序号进行减一的操作
                preSelectIds?.takeIf { currentItem != null }?.apply {
                    val indexOf = this.indexOf(currentItem)
                    if (indexOf >= 0) {
                        for (index in indexOf + 1 until size) {
                            val data = mData.filter { photoItemModel ->
                                photoItemModel.id == this[index].id
                            }
                            data[0].let {
                                it.selectNumber = --it.selectNumber
                            }
                        }
                        // 这个地方使用DiffUtil
                        holder.itemView.post {
                            val calculateDiff = DiffUtil.calculateDiff(
                                DiffCallback(
                                    oldData,
                                    mData,
                                    DiffEnum.IMAGE_SELECTOR_UPDATE_NUMBER
                                ), true
                            )
                            calculateDiff.dispatchUpdatesTo(this@ImageSelectorAdapter)
                        }
                    }
                }
            }
        }
    }

    /**
     * 获取图片的大小
     * @return Int
     */
    private fun getImageResize(context:Context): Int {
        if (mImageResize == 0) {
            val gridLayoutManager = recyclerView.layoutManager as GridLayoutManager
            val spanCount = gridLayoutManager.spanCount
            val screenWidth  = context.resources.displayMetrics.widthPixels
            val availableWidth = screenWidth - (context.resources.getDimensionPixelSize(
                R.dimen
                    .selector_grid_item_spacing
            ) * (spanCount - 1))

            mImageResize = availableWidth / spanCount
        }
        return mImageResize
    }


    companion object {

        /** 刷新选中状态的Code */
        const val NOTIFY_REFRESH_SELECT_CODE = 1000

        /** TAG */
        const val TAG = "ImageSelectorAdapter"
    }

    /** ViewHolder */
    class ImgSelectorViewHolder(var binding: ItemImageBinding) : RecyclerView.ViewHolder(binding.root)
}