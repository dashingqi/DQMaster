package com.dashingqi.dqimageselector.activity

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.loader.app.LoaderManager
import androidx.loader.content.Loader
import com.dashingqi.dqimageselector.R
import com.dashingqi.dqimageselector.adapter.ImageSelectorAdapter
import com.dashingqi.dqimageselector.adapter.SelectorItemDecoration
import com.dashingqi.dqimageselector.control.SelectorControl
import com.dashingqi.dqimageselector.databinding.ActivityImageSelectorBinding
import com.dashingqi.dqimageselector.listeenr.IControllerView
import com.dashingqi.dqimageselector.listeenr.IPhotoItemListener
import com.dashingqi.dqimageselector.model.ConfigData
import com.dashingqi.dqimageselector.model.PhotoItemModel

/**
 * 图片选择页面
 */
class ImageSelectorActivity : AppCompatActivity(), IPhotoItemListener,IControllerView {

    /** 配置的数据项*/
    private var mConfigData: ConfigData? = null

    /** LoaderManager Instance */
    private val mLoaderManager by lazy {
        LoaderManager.getInstance(this)
    }

    /** SelectorControl */
    private var mSelectorControl :SelectorControl?=null

    /** adapter */
    private val adapter: ImageSelectorAdapter by lazy {
        ImageSelectorAdapter()
    }

    /** ActivityImageSelectorBinding */
    private val binding by lazy {
        ActivityImageSelectorBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        mConfigData = intent?.getParcelableExtra(KEY_CONFIG_DATA)
        adapter.setItemListener(this)
        adapter.setConfigData(mConfigData)
        // 设置分割线
        binding.recyclerView.addItemDecoration(SelectorItemDecoration(LINE_COUNT))
        binding.recyclerView.adapter = adapter
        mSelectorControl = SelectorControl(this, mLoaderManager,this)
        handlePermission()
    }


    /**
     * 处理权限
     * 当获取到权限就去手机中查询数据
     * 没有获取到权限就去申请权限
     */
    private fun handlePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, WRITE_PERMISSION)
                != PackageManager.PERMISSION_GRANTED
            ) {
                // 没有获取到权限在做出判断
                if (ActivityCompat
                        .shouldShowRequestPermissionRationale(this, WRITE_PERMISSION)
                ) {
                    // 弹出对话框让用户去设置页面进行开启

                } else {
                    //做权限的申请
                    ActivityCompat
                        .requestPermissions(
                            this, arrayOf(WRITE_PERMISSION),
                            WRITE_PERMISSION_REQUEST_CODE
                        )
                }
            } else {
                mSelectorControl?.fetchData()
            }
        } else {
            mSelectorControl?.fetchData()
        }
    }
    /**
     * 权限申请结果处理
     */
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            WRITE_PERMISSION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty()) {
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        // 申请成功
                        mSelectorControl?.fetchData()
                    }
                }
            }
        }
    }

    /**
     * Item点击事件的回调
     */
    override fun onItemClick(position: Int, photoItem: PhotoItemModel?) {
        photoItem?.let {
            ImagePreviewActivity.startPreviewActivity(
                this,
                it,
                ImagePreviewActivity.PREVIEW_VIEW_REQUEST_CODE
            )
        }
    }

    /**
     * 选择按钮点击事件的回调
     */
    override fun onSelectClick(position: Int, photoItem: PhotoItemModel?) {
        adapter.notifyItemChanged(position, ImageSelectorAdapter.NOTIFY_REFRESH_SELECT_CODE)
    }

    /**
     * 更新编辑按钮
     */
    override fun updateEditView() {
        updateViewState(adapter.mSelectedItems.size)
    }

    /**
     * 用于更新预览和完成按钮的状态
     */
    private fun updateViewState(selectorSize: Int) {
        val viewState = selectorSize > 0
        // 预览按钮
        binding.tvPreview.apply {
            isSelected = viewState
            isEnabled = viewState
        }
        // 完成按钮
        binding.btnFinish.apply {
            isSelected = viewState
            isEnabled = viewState
            text = if (viewState) {
                resources.getString(R.string.finish_text) +
                        resources.getString(R.string.finish_number, selectorSize)
            } else {
                resources.getString(R.string.finish_text)
            }
        }
    }

    override fun onPreCreateLoader(id: Int) {
    }

    override fun onLoadFinish(data: MutableList<PhotoItemModel>) {
       if (data.isNotEmpty()){
           runOnUiThread {
               adapter.let {
                   it.mData.addAll(data)
                   it.notifyDataSetChanged()
               }
           }
       }
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {
    }


    companion object {

        /** 写权限*/
        const val WRITE_PERMISSION = Manifest.permission.WRITE_EXTERNAL_STORAGE

        /** 权限请求的code*/
        const val WRITE_PERMISSION_REQUEST_CODE = 10000

        /** RecyclerView每一行展示的子View的个数*/
        const val LINE_COUNT = 4

        /**
         * TAG
         */
        const val TAG = "ImageSelectorActivity"

        /**
         * key 配置的数据项
         */
        const val KEY_CONFIG_DATA = "key_config_data"

        /**
         * 跳转Activity
         */
        fun start(activity: Activity, requestCode: Int, configDataData: ConfigData) {
            Intent(activity, ImageSelectorActivity::class.java).apply {
                putExtra(KEY_CONFIG_DATA, configDataData)
                activity.startActivityForResult(this, requestCode)
            }
        }
    }
}