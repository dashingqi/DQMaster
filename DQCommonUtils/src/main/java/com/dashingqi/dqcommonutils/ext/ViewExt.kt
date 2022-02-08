package com.dashingqi.dqcommonutils.ext

import android.view.View
import android.view.ViewGroup

/**
 * @desc : View的扩展方法
 * @author : zhangqi
 * @time : 2022/2/8 23:01
 */

inline fun <reified T : ViewGroup.LayoutParams> View.updateLayoutParams(block: T.() -> Unit) {
    val params = layoutParams as T
    block(params)
    layoutParams = params
}

fun View.updateMargin(left: Int? = null, top: Int? = null, right: Int? = null, bottom: Int? = null) {
    (layoutParams as? ViewGroup.MarginLayoutParams)?.let { param ->
        updateLayoutParams<ViewGroup.MarginLayoutParams> {
            left?.let {
                param.marginStart = left
            }

            right?.let {
                param.marginEnd = right
            }

            top?.let {
                param.topMargin = top
            }

            bottom?.let {
                param.bottomMargin = bottom
            }
        }
    }
}