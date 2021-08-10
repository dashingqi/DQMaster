package com.dashingqi.dqimageselector.model

import android.os.Parcelable
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

/**
 * kotlin中将数据序列化的话 需要将变量写在构造方法中，
 * 如果写在类的作用域中是不起作用的
 * @author : zhangqi
 * @time : 2021/8/5
 * desc : 配置的数据项
 */
@Parcelize
class ConfigData(
    /** 是否要展示拍照按钮 */
    var isShowCamera: Boolean = false,
    /** 设置选择的最大数 */
    var maxSelectSize: Int = 9
) : Parcelable