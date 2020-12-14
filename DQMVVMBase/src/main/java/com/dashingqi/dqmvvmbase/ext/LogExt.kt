package com.dashingqi.dqmvvmbase.ext

import android.util.Log

/**
 * @author : zhangqi
 * @time : 12/14/20
 * desc :
 */

const val TAG = "LOG_EXT"

/**
 * 控制打印Log的开关
 */
const val IS_DEBUG = true

enum class LEVEL {
    V, D, I, W, E
}

fun String.logD(tag: String = TAG) {
    log(LEVEL.D, tag, this)
}

fun String.logV(tag: String = TAG) {
    log(LEVEL.V, tag, this)
}

fun String.logW(tag: String = TAG) {
    log(LEVEL.W, tag, this)
}

fun String.logI(tag: String = TAG) {
    log(LEVEL.I, tag, this)
}


fun String.logE(tag: String = TAG) {
    log(LEVEL.E, tag, this)
}

private fun log(level: LEVEL, tag: String, value: String) {
    if (!IS_DEBUG) return
    when (level) {
        LEVEL.V -> {
            Log.v(tag, value)
        }

        LEVEL.D -> {
            Log.d(tag, value)
        }

        LEVEL.I -> {
            Log.i(tag, value)
        }

        LEVEL.W -> {
            Log.w(tag, value)
        }

        LEVEL.E -> {
            Log.e(tag, value)
        }
    }
}