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
    constructor(context: Context) : super(context, R.style.base_dialog_theme_transparent)

    init {
        setDialogGravity(Gravity.BOTTOM)
        //设置弹出和隐藏时的动画
        setDialogInAndOutAnimation(R.style.bottom_dialog_animation)

    }

    /**
     * 为什么要在setContentView中设置 setDialogWidthPercent？
     * 应为添加完View之后 才能进行设置View的宽度
     */
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