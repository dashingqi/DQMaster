package com.dashingqi.dqimageselector.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.dashingqi.dqimageselector.R
import com.dashingqi.dqimageselector.model.PhotoItemModel
import com.github.chrisbanes.photoview.PhotoView

/**
 * 图片预览界面
 */
class ImagePreviewActivity : AppCompatActivity() {
    private var mPhotoView: PhotoView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_preview)
        mPhotoView = findViewById(R.id.photoView)
        val model: PhotoItemModel? = intent?.getParcelableExtra(KEY_PREVIEW_PHOTO_MODEL)
        show(model)
    }

    companion object {

        /** Preview request code */
        const val PREVIEW_VIEW_REQUEST_CODE = 9999

        /** key photo model */
        const val KEY_PREVIEW_PHOTO_MODEL = "key_preview_photo_model"

        /**
         * 开启图片预览界面
         * @param context Context 上下文环境
         * @param model PhotoItemModel 当前点击的Item
         * @param requestCode Int 请求的code
         */
        fun startPreviewActivity(context: Activity, model: PhotoItemModel, requestCode: Int) {
            Intent(context, ImagePreviewActivity::class.java).apply {
                putExtra(KEY_PREVIEW_PHOTO_MODEL, model)
                context.startActivityForResult(this, requestCode)
            }
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
            mPhotoView?.let {
                Glide.with(this).load(photoModel.path).into(it)
            }
        }
    }
}