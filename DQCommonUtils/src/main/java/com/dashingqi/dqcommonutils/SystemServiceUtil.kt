package com.dashingqi.dqcommonutils

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context

/**
 * @author : zhangqi
 * @time : 12/10/20
 * desc : 系统服务工具类
 */
object SystemServiceUtil {

    /**
     * 复制文本到剪切板上
     * content:文本内容
     * context:上下文环境
     * return：true 操作成功 false：操作失败
     */
    fun copy(content: String?): Boolean {
        //获取剪贴板管理器：
        AppUtil.application?.let {
            val cm = it.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager?
            cm?.takeIf { !content.isNullOrEmpty() }?.apply {
                val mClipData = ClipData.newPlainText("Label", content)
                this.setPrimaryClip(mClipData)
                return true
            }
        }

        return false
    }
}