package com.dashingqi.dqimageselector.selection

import com.dashingqi.dqimageselector.enum.MimeTypeEnum

/**
 * @desc : 具体的配置信息类
 * @author : zhangqi
 * @time : 2021/8/24 01:23
 */
object SelectionIns {

    /** 设置最大选择媒体文件的数量 */
    var mMaxSize: Int = 9

    /** 能否拍照片*/
    var mCapture: Boolean = false

    /** 媒体文件的类别*/
    var mMimeTypeSet: Set<MimeTypeEnum>? = null
}