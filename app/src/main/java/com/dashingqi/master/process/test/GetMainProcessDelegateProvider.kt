package com.dashingqi.master.process.test

import android.app.ActivityManager
import android.content.Context
import android.os.Bundle
import com.dashingqi.master.process.ipc.AbsDelegateProvider
import com.dashingqi.master.process.ipc.callOnMainProcessByProvider
import com.dashingqi.master.process.ipc.logD
import com.dashingqi.master.process.test.GetMainProcessDelegateProvider.Companion.KEY_MAIN_PROCESS_DATA

/**
 * @desc :
 * @author : zhangqi
 * @time : 2023/12/3 17:54
 */
class GetMainProcessDelegateProvider : AbsDelegateProvider() {
    override fun executeCall(context: Context?, params: Bundle?): Bundle {
        val currentProcessName = getCurrentProcessName(context)
        currentProcessName.logD()
        val bundle = Bundle()
        // 在主进程执行,获取数据，存储数据
        val mainProcessData = getData()
        bundle.putString(KEY_MAIN_PROCESS_DATA, mainProcessData)
        return bundle
    }

    companion object {
        const val KEY_MAIN_PROCESS_DATA = "keyMainProcessData"
    }
}

fun getMainProcessData(context: Context, params: Bundle?) {
    val currentProcessName = getCurrentProcessName(context)
    currentProcessName.logD()
    val delegateResult = callOnMainProcessByProvider(context, params, GetMainProcessDelegateProvider::class.java)
    val resultCode = delegateResult.resultCode
    "$resultCode".logD()
    if (delegateResult.resultBundle == null) {
        "resultBundle is null".logD()
        return
    }
    val data = delegateResult.resultBundle.getString(KEY_MAIN_PROCESS_DATA, "main process data is null")
    data.logD()
}


fun getCurrentProcessName(context: Context?): String? {
    context ?: return null
    val pid = android.os.Process.myPid()
    val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
    val runningProcesses = activityManager.runningAppProcesses
    for (processInfo in runningProcesses) {
        if (processInfo.pid == pid) {
            return processInfo.processName
        }
    }
    return null
}