package com.dashingqi.dqdialog

import android.content.Context
import android.view.Gravity
import android.view.View
import android.view.ViewGroup

/**
 * @author : zhangqi
 * @time : 2020/9/16
 * desc :
 */
open class BottomDialog : BaseDialog {
    constructor(context: Context) : super(context, R.style.base_loadingDialogTheme)

    init {
        setDialogGravity(Gravity.BOTTOM)
        //设置弹出和隐藏时的动画
        setDialogInAndOutAnimation(R.style.bottom_dialog_animation)

    }

    override fun setContentView(view: View) {
        super.setContentView(view)
        setDialogWidthPercent(1f)
    }

    override fun setContentView(layoutResID: Int) {
        super.setContentView(layoutResID)
        setDialogWidthPercent(1f)
    }

    override fun setContentView(view: View, params: ViewGroup.LayoutParams?) {
        super.setContentView(view, params)
        setDialogWidthPercent(1f)
    }


}