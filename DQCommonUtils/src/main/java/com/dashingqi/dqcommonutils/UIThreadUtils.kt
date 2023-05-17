package com.dashingqi.dqcommonutils

import android.os.Handler
import android.os.Looper

/**
 * 在UI线程中执行任务的工具类
 * @author zhangqi
 * @since 2021/4/1
 */
object UIThreadUtils {

    private var sMainHandler: Handler? = null

    /**
     * 判断是否在 UI 线程中
     * @return true/false
     */
    fun isOnUiThread() = Looper.getMainLooper().thread == Thread.currentThread()

    /**
     * 在UI线程中执行，如果当前在UI线程中就直接执行任务，如果不在UI线程就向MainHandler post这个任务
     */
    fun runOnUiThread(action: Runnable) {
        if (Thread.currentThread() != Looper.getMainLooper().thread) {
            getMainHandler()?.post(action)
        } else {
            action.run()
        }
    }

    /**
     * 在UI线程中执行，可延时。
     */
    fun runOnUiThread(action: Runnable, delayMillis: Long) {
        if (delayMillis > 0) {
            val mainHandler = getMainHandler()
            mainHandler?.postDelayed(action, delayMillis)
        } else {
            runOnUiThread(action)
        }
    }

    /**
     * 在UI线程中执行，如果当前线程是UI线程，就立刻执行；如果当前线程不是UI线程，那么该Runnable被post到队列的最前面
     */
    fun runOnUiThreadAtFrontOfQueue(action: Runnable) {
        if (Thread.currentThread() != Looper.getMainLooper().thread) {
            val mainHandler = getMainHandler()
            mainHandler?.postAtFrontOfQueue(action)
        } else {
            action.run()
        }
    }

    /**
     * 得到关联到主线程的Handler
     */
    fun getMainHandler(): Handler? {
        if (null == sMainHandler) {
            synchronized(UIThreadUtils::class.java) {
                if (null == sMainHandler) {
                    sMainHandler = Handler(Looper.getMainLooper())
                }
            }
        }
        return sMainHandler
    }
}