package com.dashingqi.dqimageselector.listeenr

import com.dashingqi.dqimageselector.model.PhotoItemModel

/**
 * 预览界面中被选中Item的点击事件接口类
 * @author : zhangqi
 * @time : 2021/8/22
 * desc :
 */
interface IPreviewSelectedItemListener {

    /**
     * Item的点击事件
     *
     */
    fun onPreSelectedItemClick(position: Int, itemModel: PhotoItemModel)
}