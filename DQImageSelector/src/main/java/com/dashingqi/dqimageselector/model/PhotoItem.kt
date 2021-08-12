package com.dashingqi.dqimageselector.model

/**
 * @author : zhangqi
 * @time : 2021/8/7
 * desc : 照片数据模型
 */
class PhotoItemModel(
    //  图片ID
    var id: String,
    // 图片的地址
    var path: String,
    // 图片名称
    var name: String,
    // 图片创建的日期
    var date: Long,
    // 图片是否被选中
    var isSelected: Boolean = false,
    // 选择的序号
    var selectNumber: Int = -1
)