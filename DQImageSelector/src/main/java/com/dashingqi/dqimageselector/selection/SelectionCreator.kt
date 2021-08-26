package com.dashingqi.dqimageselector.selection

import android.content.Intent
import com.dashingqi.dqimageselector.activity.XHYSelectorActivity
import com.dashingqi.dqimageselector.engine.ImageLoadEngine
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
    fun setMaxSize(maxSize: Int): SelectionCreator {
        SelectionIns.mMaxSize = maxSize
        return this
    }

    /**
     * 是否提供拍照功能
     * @param capture Boolean true/false
     */
    fun capture(capture: Boolean): SelectionCreator {
        SelectionIns.mCapture = capture
        return this
    }

    /**
     * 图片加载的引擎
     * @param engine ImageLoadEngine 引擎接口类
     */
    fun engine(engine: ImageLoadEngine): SelectionCreator {
        SelectionIns.mEngine = engine
        return this
    }

    /**
     * 开启图片选择界面
     * @param requestCode Int 请求code
     */
    fun startForResult(requestCode: Int) {
        xhy.getActivity()?.let { activity ->
            Intent(activity, XHYSelectorActivity::class.java).apply {
                xhy.getFragment()?.let {
                    it.startActivityForResult(this, requestCode)
                    return@apply
                }
                activity.startActivityForResult(this, requestCode)
            }
        }
    }
}