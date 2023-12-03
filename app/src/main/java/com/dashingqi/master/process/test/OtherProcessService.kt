package com.dashingqi.master.process.test

import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import com.dashingqi.master.process.ipc.logD
import com.dashingqi.master.process.test.getMainProcessData

/**
 * @desc :
 * @author : zhangqi
 * @time : 2023/12/3 17:53
 */
class OtherProcessService : Service() {

    private val handler = Handler(Looper.getMainLooper())
    override fun onBind(intent: Intent?): IBinder? = null

    override fun onCreate() {
        super.onCreate()
        "Service onCreate Perform".logD()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        "Service onStartCommand Perform".logD()
        handler.postDelayed({
            val mainProcessData = getMainProcessData(this.applicationContext, null)
        }, 2000L)
        return super.onStartCommand(intent, flags, startId)

    }


}