package com.dashingqi.dqcommonutils

import android.annotation.SuppressLint
import android.app.Application
import com.dashingqi.dqcommonutils.provider.AppContentProvider
import java.lang.Exception

/**
 * @author : zhangqi
 * @time : 12/10/20
 * desc :
 */
@SuppressLint("PrivateApi")
object AppUtil {

    val application: Application by lazy {

        var app = AppContentProvider.application
        if (app == null) {
            //通过反射获取到ActivityThread中的Application
            app = getApplicationInstanceByReflect()
        }
        if (app == null) {
            throw Exception("AppUtil init failed ,application is null")
        }
        app
    }

    private val activityThreadClass by lazy {
        Class.forName("android.app.ActivityThread")
    }

    /**
     * 通过反射拿到Application的实例
     * 这里面通过ActivityThread中getApplication()方法拿到Application实例
     * 1. 通过发射拿到ActivityThread 的 Class
     * 2. 拿到名字为 getApplication()方法 Method
     * 3. 执行方法的时候需要传入当前方法依附的 类的实例
     */
    private fun getApplicationInstanceByReflect(): Application? {
        getActivityThreadInstance()?.let {
            val app = activityThreadClass.getMethod("getApplication")?.invoke(it, null)
            return app as Application
        }
        return null

    }

    /**
     * 通过反射获取到ActivityThread 有两种
     * 1. 拿到变量sCurrentActivityThread的Field
     * 2. 通过currentActivityThread()拿到
     */
    private fun getActivityThreadInstance(): Any? {
        return getActivityThreadInstanceByField()?.let {
            it
        } ?: getActivityThreadInstanceByMethod()
    }

    /**
     * 通过Field获取到ActivityThread的实例
     */
    private fun getActivityThreadInstanceByField(): Any? {
        try {
            var currentActivityThreadField = activityThreadClass.getDeclaredField("sCurrentActivityThread")
            //可以获取到私有的属性
            currentActivityThreadField.isAccessible = true
            //可以拿到静态的变量
            return currentActivityThreadField.get(null)
        } catch (exception: Exception) {
            exception.printStackTrace()
        }
        return null
    }

    /**
     * 通过Method获取到ActivityThread的实例
     */
    private fun getActivityThreadInstanceByMethod(): Any? {
        try {
            return activityThreadClass.getMethod("currentActivityThread").invoke(null)
        } catch (exception: Exception) {
            exception.printStackTrace()
        }
        return null

    }
}