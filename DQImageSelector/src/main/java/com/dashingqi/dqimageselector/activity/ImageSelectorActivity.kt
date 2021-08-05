package com.dashingqi.dqimageselector.activity

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.dashingqi.dqimageselector.R
import com.dashingqi.dqimageselector.model.ConfigData

/**
 * 图片选择页面
 */
class ImageSelectorActivity : AppCompatActivity() {

    /** 配置的数据项*/
    private var mConfigData: ConfigData? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_selector)
        mConfigData = intent?.getParcelableExtra(KEY_CONFIG_DATA)
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
            }
        } else {
            fetchData()
        }
    }

    /**
     * 获取数据
     */
    private fun fetchData() {

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
                if (grantResults.isNotEmpty()){
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
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