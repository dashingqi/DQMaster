package com.dashingqi.dqcommonutils

import android.util.Log
import android.view.MotionEvent

/**
 * @author : zhangqi
 * @time : 12/5/20
 * desc : 事件的工具类
 */
object MotionEventUtils {

    /**
     * eventId:事件Id
     * currentMethod:当前事件所处于的方法
     * tag：log的tag
     */
    fun printEvent(eventId: Int, currentMethod: String, tag: String) {
        when (eventId) {
            MotionEvent.ACTION_DOWN -> {
                Log.d(tag, "$currentMethod -----> action_down")
            }

            MotionEvent.ACTION_MOVE -> {
                Log.d(tag, "$currentMethod -----> action_move ")
            }
            MotionEvent.ACTION_UP -> {
                Log.d(tag, "$currentMethod ------> action_up")
            }
            MotionEvent.ACTION_CANCEL -> {
                Log.d(tag, "$currentMethod -----> action_cancel")
            }
        }
    }
}