package com.dashingqi.dqdialog.animation

import android.graphics.Color

/**
 * @author : zhangqi
 * @time : 2020/9/16
 * desc :
 */
class WhiteLoadingDrawable : ProgressDrawable() {

    init {
        //设置成白色
        mPaint.color = Color.parseColor("#ffffff")
    }
}