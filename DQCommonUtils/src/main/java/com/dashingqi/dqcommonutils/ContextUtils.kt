package com.dashingqi.dqcommonutils

import android.app.Activity
import android.content.Context
import android.view.ContextThemeWrapper

/**
 * @author : zhangqi
 * @time : 2020/8/26
 * desc : 上下文工具类
 */
object ContextUtils {

    fun getActivity(context :Context):Activity?{
        return when (context) {
            is Activity -> {
                context as Activity
            }
            is ContextThemeWrapper -> {
                getActivity((context as ContextThemeWrapper).baseContext)
            }
            is androidx.appcompat.view.ContextThemeWrapper -> {
                getActivity((context as androidx.appcompat.view.ContextThemeWrapper))
            }
            else -> null
        }
    }
}