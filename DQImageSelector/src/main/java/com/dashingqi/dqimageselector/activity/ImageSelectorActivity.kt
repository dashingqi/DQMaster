package com.dashingqi.dqimageselector.activity

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import androidx.recyclerview.widget.RecyclerView
import com.dashingqi.dqimageselector.R
import com.dashingqi.dqimageselector.adapter.ImageSelectorAdapter
import com.dashingqi.dqimageselector.adapter.SelectorItemDecoration
import com.dashingqi.dqimageselector.listeenr.IPhotoItemListener
import com.dashingqi.dqimageselector.model.ConfigData
import com.dashingqi.dqimageselector.model.PhotoItemModel

/**
 * 图片选择页面
 */
class ImageSelectorActivity : AppCompatActivity(), IPhotoItemListener {

    /** 配置的数据项*/
    private var mConfigData: ConfigData? = null

    /** 预览*/
    private var mTvPreview: TextView? = null

    /** 完成*/
    private var mBtnFinish: Button? = null

    /** LoaderManager Instance */
    private val mLoaderManager by lazy {
        LoaderManager.getInstance(this)
    }

    /** adapter */
    private var adapter: ImageSelectorAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_selector)
        mConfigData = intent?.getParcelableExtra(KEY_CONFIG_DATA)
        Log.d(TAG, "isShowCamera = ${mConfigData?.isShowCamera} maxSize = ${mConfigData?.maxSelectSize}")
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        mTvPreview = findViewById(R.id.tvPreview)
        mBtnFinish = findViewById(R.id.btnFinish)
        adapter = ImageSelectorAdapter()
        adapter?.setItemListener(this)
        adapter?.setConfigData(mConfigData)
        // 设置分割线
        recyclerView.addItemDecoration(SelectorItemDecoration(LINE_COUNT))
        recyclerView.adapter = adapter
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
                fetchData()
            }
        } else {
            fetchData()
        }
    }

    /**
     * 获取数据
     */
    private fun fetchData() {
        mLoaderManager.initLoader(LOADER_ALL, null, object : LoaderManager.LoaderCallbacks<Cursor> {
            override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
                return CursorLoader(
                    this@ImageSelectorActivity,
                    MEDIA_STORE_IMAGE_URI, IMAGE_PROJECTION,
                    null, null,
                    IMAGE_PROJECTION[2] + " DESC"
                )
            }

            @SuppressLint("Range")
            override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
                data?.let { cursor ->
                    val tempData = mutableListOf<PhotoItemModel>()
                    while (cursor.moveToNext()) {
                        val path = cursor.getString(cursor.getColumnIndex(IMAGE_PROJECTION[0]))
                        val name = cursor.getString(cursor.getColumnIndex(IMAGE_PROJECTION[1]))
                        val date = cursor.getLong(cursor.getColumnIndex(IMAGE_PROJECTION[2]))
                        val id = cursor.getString(cursor.getColumnIndex(IMAGE_PROJECTION[3]))
                        Log.d(TAG, "path = $path name = $name date = $date id = $id")
                        val photoItemModel = PhotoItemModel(id, path, name, date)
                        tempData.add(photoItemModel)
                    }
                    runOnUiThread {
                        if (tempData.isNotEmpty()) {
                            adapter?.let {
                                it.mData.addAll(tempData)
                                it.notifyDataSetChanged()
                            }
                        }
                    }
                }
            }

            override fun onLoaderReset(loader: Loader<Cursor>) {
            }

        })

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
                        fetchData()
                    }
                }
            }
        }
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

        /** 查询手机内部图片对应的URI */
        val MEDIA_STORE_IMAGE_URI: Uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI

        /** 查询数据库中的字段*/
        val IMAGE_PROJECTION = arrayOf(
            MediaStore.Images.Media.DATA,
            MediaStore.Images.Media.DISPLAY_NAME,
            MediaStore.Images.Media.DATE_ADDED,
            MediaStore.Images.Media._ID
        )

        const val LOADER_ALL = 10000


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
        adapter?.notifyItemChanged(position, ImageSelectorAdapter.NOTIFY_REFRESH_SELECT_CODE)
        updateViewState(adapter?.mSelectedItems?.size ?: 0)
    }

    /**
     * 用于更新预览和完成按钮的状态
     */
    private fun updateViewState(selectorSize: Int) {
        val viewState = selectorSize > 0
        mTvPreview?.isSelected = viewState
        mBtnFinish?.isSelected = viewState
    }
}