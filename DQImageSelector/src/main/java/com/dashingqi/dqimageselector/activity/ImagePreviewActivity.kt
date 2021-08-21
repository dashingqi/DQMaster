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
import com.dashingqi.dqimageselector.databinding.ActivityImagePreviewBinding
import com.dashingqi.dqimageselector.model.PhotoItemModel
import com.dashingqi.dqimageselector.utils.MediaStoreUtil
import com.dashingqi.dqimageselector.utils.VersionUtil

/**
 * 图片预览界面
 */
class ImagePreviewActivity : AppCompatActivity() {

    /** ActivityImagePreviewBinding */
    private val binding by lazy {
        ActivityImagePreviewBinding.inflate(layoutInflater)
    }

    /** 展示所有图片的Adapter*/
    private val previewAllAdapter by lazy {
        ImagePreviewAllAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initAllDataRVAdapter()
        intent?.let {
            val model: PhotoItemModel? = it.getParcelableExtra(KEY_PREVIEW_PHOTO_MODEL)
            val allPhotoModelList =
                it.getParcelableArrayListExtra<PhotoItemModel>(KEY_PREVIEW_ALL_PHOTO_MODEL_LIST)
            Log.d(TAG, "allPhotoModeList size is ${allPhotoModelList?.size ?: 0}")
            val selectedPhotoModelList =
                it.getParcelableArrayListExtra<PhotoItemModel>(KEY_PREVIEW_SELECTED_PHOTO_MODEL_LIST)
            Log.d(TAG, "selectedPhotoModelList size is ${selectedPhotoModelList?.size ?: 0}")
            show(model)
            showListData(allPhotoModelList, selectedPhotoModelList)
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
     * 用于展示全部图片和选中的图片
     * @param allPhotoModel ArrayList<PhotoItemModel> 全部图片
     * @param selectedPhotoModel ArrayList<PhotoItemModel> 选中的图片
     */
    private fun showListData(
        allPhotoModel: ArrayList<PhotoItemModel>?, selectedPhotoModel:
        ArrayList<PhotoItemModel>?
    ) {

        // 更新全部图片布局
        allPhotoModel?.takeIf { it.isNotEmpty() }?.apply {

            previewAllAdapter.setData(this)
        }

        // 更新选中的图片布局
        selectedPhotoModel?.takeIf { it.isNotEmpty() }?.apply {

        }

    }

    /**
     * 重写返回键事件
     */
    override fun onBackPressed() {
        finish()
    }

    /**
     * 展示逻辑
     * @param model PhotoItemModel? 展示的数据
     */
    private fun show(model: PhotoItemModel?) {
        model?.let { photoModel ->
            // 适配Android Q
            Glide.with(this).load(if (VersionUtil.isAndroidQ()) photoModel.uri else photoModel.path)
                .into(binding.photoView)
        }
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
}