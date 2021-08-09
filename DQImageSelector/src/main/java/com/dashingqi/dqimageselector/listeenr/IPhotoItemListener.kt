package com.dashingqi.dqimageselector.listeenr

import com.dashingqi.dqimageselector.model.PhotoItemModel

/**
 * @author : zhangqi
 * @time : 2021/8/9
 * desc :
 */
interface IPhotoItemListener {

    /** 图片Item的点击事件*/
    fun onItemClick(position: Int, photoItem: PhotoItemModel)

    /** 选择按钮的点击*/
    fun onSelectClick(position: Int, photoItem: PhotoItemModel)
}