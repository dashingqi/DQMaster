package com.dashingqi.dqcommonutils

import android.content.Context
import android.view.Gravity
import android.widget.Toast
import androidx.annotation.StringRes

/**
 * @author : zhangqi
 * @time : 2020/9/25
 * desc :
 */

fun Context.toastShort(message: String, gravity: Int = Gravity.BOTTOM, duration: Int = Toast.LENGTH_SHORT) {
    toast(message, gravity, duration)
}

fun Context.toastShort(@StringRes message: Int) {
    toastLong(getString(message))
}


fun Context.toastLong(message: String, gravity: Int = Gravity.BOTTOM, duration: Int = Toast.LENGTH_LONG) {
    toast(message, gravity, duration)
}

fun Context.toastLong(@StringRes message: Int) {
    toastLong(getString(message))
}

fun Context.toast(message: String, gravity: Int = Gravity.BOTTOM, duration: Int = Toast.LENGTH_SHORT) {
    val toast = Toast.makeText(this,message,duration)
    toast.setGravity(gravity, 0, 0)
    toast.show()
}