package com.dashingqi.dqcommontoolbar.utils

import android.app.Activity
import android.content.Context
import android.view.ContextThemeWrapper

/**
 * @author : zhangqi
 * @time : 2020/8/25
 * desc :
 */
object DQContextUtils {

    fun getActivity(context: Context): Activity? {

        return when (context) {
            is Activity -> {
                context
            }
            is ContextThemeWrapper -> {
                getActivity(context.baseContext)
            }
            is androidx.appcompat.view.ContextThemeWrapper -> {
                getActivity(context.baseContext)
            }
            else -> null
        }
    }
}