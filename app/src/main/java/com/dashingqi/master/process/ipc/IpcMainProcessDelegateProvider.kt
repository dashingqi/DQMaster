package com.dashingqi.master.process.ipc

import android.net.Uri

/**
 * @desc : 主进程代理的 Provider
 * @author : zhangqi
 * @time : 2023/12/3 11:22
 */


object IpcMainProcessDelegateProvider : IpcDelegateProvider() {
    private val MAIN_PROVIDER_AUTHORITIES: String = "${context?.packageName}.IpcMainDelegateProvider"
    internal val PROVIDER_URI: Uri = Uri.parse("content://$MAIN_PROVIDER_AUTHORITIES")
}