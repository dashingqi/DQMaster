package com.dashingqi.dqimageselector.activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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

    }

    companion object {

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