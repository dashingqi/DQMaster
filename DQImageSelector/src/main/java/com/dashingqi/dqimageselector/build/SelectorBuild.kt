package com.dashingqi.dqimageselector.build

import android.app.Activity
import android.util.Log
import com.dashingqi.dqimageselector.activity.ImageSelectorActivity
import com.dashingqi.dqimageselector.model.ConfigData

/**
 * @author : zhangqi
 * @time : 2021/8/5
 * desc : 选择器的构建
 */
object SelectorBuild {


    /**
     * 创建一个Builder对象
     */
    fun builder(): Builder {

        return Builder()
    }

    /**
     * 用于构建打开选择器的行为对象
     */
    class Builder {

        /** 配置数据项*/
        private var mConfigData: ConfigData = ConfigData()

        /**
         * 是否展示拍照
         */
        fun isShowCamera(isShowCamera: Boolean): Builder {
            mConfigData.isShowCamera = isShowCamera
            return this
        }

        /**
         * 设置最大选择的数量
         */
        fun setMaxSelectSize(maxSelectSize: Int): Builder {
            mConfigData.maxSelectSize = maxSelectSize
            return this
        }


        /**
         * 从Activity跳转到目标Activity
         */
        fun start(activity: Activity, requestCode: Int) {
            Log.d("TAG", "size = ${mConfigData.maxSelectSize}: ")
            ImageSelectorActivity.start(activity, requestCode, mConfigData)
        }
    }
}