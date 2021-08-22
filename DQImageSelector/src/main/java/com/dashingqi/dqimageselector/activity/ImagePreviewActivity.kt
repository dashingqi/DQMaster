package com.dashingqi.dqimageselector.activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.bumptech.glide.Glide
import com.dashingqi.dqimageselector.adapter.ImagePreviewAllAdapter
import com.dashingqi.dqimageselector.adapter.ImagePreviewSelectedAdapter
import com.dashingqi.dqimageselector.adapter.ImagePreviewSelectedAdapter.Companion.NOTIFY_REFRESH_SELECT_LABEL_CODE
import com.dashingqi.dqimageselector.adapter.SelectedItemDecoration
import com.dashingqi.dqimageselector.databinding.ActivityImagePreviewBinding
import com.dashingqi.dqimageselector.listeenr.IPreviewSelectedItemListener
import com.dashingqi.dqimageselector.model.PhotoItemModel
import com.dashingqi.dqimageselector.utils.MediaStoreUtil
import com.dashingqi.dqimageselector.utils.VersionUtil
import kotlinx.coroutines.Runnable

/**
 * 图片预览界面
 */
class ImagePreviewActivity : AppCompatActivity(), IPreviewSelectedItemListener {

    /** ActivityImagePreviewBinding */
    private val binding by lazy {
        ActivityImagePreviewBinding.inflate(layoutInflater)
    }

    /** 展示所有图片的Adapter */
    private val previewAllAdapter by lazy {
        ImagePreviewAllAdapter()
    }

    /** 展示选中图片的Adapter */
    private val previewSelectedAdapter by lazy {
        ImagePreviewSelectedAdapter()
    }

    /**
     * 底部当前选中的position
     */
    private var mCurrentSelectedPosition = 0

    /** 大布局RV的数据源*/
    private var mAllPreviewList: ArrayList<PhotoItemModel>? = null

    /** 选中的数据源*/
    private var mSelectedPreList: ArrayList<PhotoItemModel>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initAllDataRVAdapter()
        initSelectedDataRvAdapter()
        intent?.let {
            val model: PhotoItemModel? = it.getParcelableExtra(KEY_PREVIEW_PHOTO_MODEL)
            mAllPreviewList =
                it.getParcelableArrayListExtra(KEY_PREVIEW_ALL_PHOTO_MODEL_LIST)
            Log.d(TAG, "allPhotoModeList size is ${mAllPreviewList?.size ?: 0}")
            mSelectedPreList =
                it.getParcelableArrayListExtra(KEY_PREVIEW_SELECTED_PHOTO_MODEL_LIST)
            Log.d(TAG, "selectedPhotoModelList size is ${mSelectedPreList?.size ?: 0}")
            showListData(mAllPreviewList, mSelectedPreList, model)
        }
    }

    /**
     * 初始化展示所有图片的Adapter
     */
    private fun initAllDataRVAdapter() {
        val allDataRVLayoutManager = LinearLayoutManager(this)
        allDataRVLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        binding.allRecyclerView.layoutManager = allDataRVLayoutManager
        val allSnapHelper = PagerSnapHelper()
        allSnapHelper.attachToRecyclerView(binding.allRecyclerView)
        binding.allRecyclerView.adapter = previewAllAdapter
    }

    /**
     * 初始化展示选中的图片Adapter
     */
    private fun initSelectedDataRvAdapter() {
        val selectedRVLayoutManager = LinearLayoutManager(this)
        selectedRVLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        binding.selectedRecyclerView.layoutManager = selectedRVLayoutManager
        binding.selectedRecyclerView.overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        binding.selectedRecyclerView.addItemDecoration(SelectedItemDecoration())
        previewSelectedAdapter.setItemClickListener(this)
        binding.selectedRecyclerView.adapter = previewSelectedAdapter
    }

    /**
     * 用于展示全部图片和选中的图片
     * @param allPhotoModel ArrayList<PhotoItemModel> 全部图片
     * @param selectedPhotoModel ArrayList<PhotoItemModel> 选中的图片
     */
    private fun showListData(
        allPhotoModel: ArrayList<PhotoItemModel>?, selectedPhotoModel:
        ArrayList<PhotoItemModel>?, clickModel: PhotoItemModel? = null
    ) {

        // 更新全部图片布局
        allPhotoModel?.takeIf { it.isNotEmpty() }?.apply {
            previewAllAdapter.setData(this)
            clickModel?.let {
                this.forEachIndexed { index, photoItemModel ->
                    if (it.id == photoItemModel.id) {
                        binding.allRecyclerView.post {
                            binding.allRecyclerView.scrollToPosition(index)
                        }
                        return@forEachIndexed
                    }
                }
            }
        }

        // 更新选中的图片布局
        selectedPhotoModel?.takeIf { it.isNotEmpty() }?.apply {
            // 设置选中布局的显隐
            binding.selectedRecyclerView.visibility = if (this.isEmpty()) View.GONE else View.VISIBLE
            previewSelectedAdapter.setData(this)
            // 当clickModel为空说明是点击预览进入的
            clickModel?.let {
                this.forEachIndexed { index, photoItemModel ->
                    if (it.id == photoItemModel.id) {
                        mCurrentSelectedPosition = index
                        return@forEachIndexed
                    }
                }
            }
            binding.selectedRecyclerView.postDelayed(Runnable {
                previewSelectedAdapter.notifyItemChanged(
                    mCurrentSelectedPosition,
                    NOTIFY_REFRESH_SELECT_LABEL_CODE
                )
            }, 200)

        }


    }

    /**
     * 重写返回键事件
     */
    override fun onBackPressed() {
        finish()
    }


    companion object {

        /** Preview request code */
        const val PREVIEW_VIEW_REQUEST_CODE = 9999

        /** key photo model */
        const val KEY_PREVIEW_PHOTO_MODEL = "key_preview_photo_model"

        /** key all PhotoModel*/
        const val KEY_PREVIEW_ALL_PHOTO_MODEL_LIST = "key_preview_all_photo_model_list"

        /** key selected PhotoModel */
        const val KEY_PREVIEW_SELECTED_PHOTO_MODEL_LIST = "key_preview_selected_photo_model_list"

        /** TAG */
        const val TAG = "ImagePreviewActivity"

        /**
         * 开启图片预览界面
         * @param context Activity 上下文环境
         * @param requestCode Int requestCode
         * @param model PhotoItemModel 当前点击的Item
         * @param allPhotoModel ArrayList<PhotoItemModel> 所有的图片集合
         * @param selectedPhotoModel ArrayList<PhotoItemModel> 选中的图片集合
         */
        fun startPreviewActivity(
            context: Activity, requestCode: Int, model: PhotoItemModel? = null,
            allPhotoModel: ArrayList<PhotoItemModel> = ArrayList(),
            selectedPhotoModel: ArrayList<PhotoItemModel> = ArrayList()
        ) {
            Intent(context, ImagePreviewActivity::class.java).apply {
                putExtra(KEY_PREVIEW_PHOTO_MODEL, model)
                val previewBundle = Bundle()
                previewBundle.putParcelableArrayList(KEY_PREVIEW_ALL_PHOTO_MODEL_LIST, allPhotoModel)
                previewBundle.putParcelableArrayList(KEY_PREVIEW_SELECTED_PHOTO_MODEL_LIST, selectedPhotoModel)
                putExtras(previewBundle)
                context.startActivityForResult(this, requestCode)
            }
        }
    }

    override fun onPreSelectedItemClick(position: Int, itemModel: PhotoItemModel) {
        previewSelectedAdapter.notifyItemChanged(mCurrentSelectedPosition, NOTIFY_REFRESH_SELECT_LABEL_CODE)
        previewSelectedAdapter.notifyItemChanged(position, NOTIFY_REFRESH_SELECT_LABEL_CODE)
        mCurrentSelectedPosition = position
        // 去更新上面RV
        mAllPreviewList?.forEachIndexed { index, photoItemModel ->
            if (itemModel.id == photoItemModel.id) {
                binding.allRecyclerView.scrollToPosition(index)
                return@forEachIndexed
            }
        }
    }
}