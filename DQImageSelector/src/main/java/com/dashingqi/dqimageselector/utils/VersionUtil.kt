package com.dashingqi.dqimageselector.utils

import android.os.Build

/**
 * 版本工具类
 * @author : zhangqi
 * @time : 2021/8/17
 */
object VersionUtil {

    /**
     * 是否是Android Q
     */
    fun isAndroidQ() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q
}