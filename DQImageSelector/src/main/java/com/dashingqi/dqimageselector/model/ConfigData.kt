package com.dashingqi.dqimageselector.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * @author : zhangqi
 * @time : 2021/8/5
 * desc : 配置的数据项
 */
@Parcelize
class ConfigData : Parcelable {

    /** 是否展示照相*/
    var isShowCamera = false

    /** 最多的选择数量*/
    var maxSelectSize = 9

}