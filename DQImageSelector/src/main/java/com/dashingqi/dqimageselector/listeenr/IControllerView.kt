package com.dashingqi.dqimageselector.listeenr

import android.database.Cursor
import androidx.loader.content.Loader
import com.dashingqi.dqimageselector.model.PhotoItemModel

/**
 * 控制层接口类
 * @author : zhangqi
 * @time : 2021/8/15
 * desc :
 */
interface IControllerView {

    /** 创建Loader之前回调 */
    fun onPreCreateLoader(id: Int)
    /**
     * 数据加载完成
     */
    fun onLoadFinish(data:MutableList<PhotoItemModel>)

    /** Loader 重置*/
    fun onLoaderReset(loader: Loader<Cursor>)
}