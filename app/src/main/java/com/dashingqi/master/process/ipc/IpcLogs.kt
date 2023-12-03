package com.dashingqi.master.process.ipc

import android.util.Log


const val IPC_TAG = "IcpDelegateTag"
fun String?.logD() {
    Log.d(IPC_TAG, this ?: "this is error")
}