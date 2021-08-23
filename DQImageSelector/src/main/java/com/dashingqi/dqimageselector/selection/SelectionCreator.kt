package com.dashingqi.dqimageselector.selection

import com.dashingqi.dqimageselector.enum.MimeTypeEnum
import com.dashingqi.dqimageselector.xhy.XHY

/**
 * @desc : 选择信息的构建者
 * @author : zhangqi
 * @time : 2021/8/24 01:23
 */
class SelectionCreator(var xhy: XHY, var mimeTypes: Set<MimeTypeEnum>) {

    init {
        SelectionIns.mMimeTypeSet = mimeTypes
    }

    /**
     * 设置最大选择数量
     * @param maxSize Int
     */
    fun setMaxSize(maxSize: Int) {
        SelectionIns.mMaxSize = maxSize
    }

    /**
     * 是否提供拍照功能
     * @param capture Boolean true/false
     */
    fun capture(capture: Boolean) {
        SelectionIns.mCapture = capture
    }

}