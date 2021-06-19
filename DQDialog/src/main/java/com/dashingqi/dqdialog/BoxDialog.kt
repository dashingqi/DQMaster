package com.dashingqi.dqdialog

import android.content.Context
import android.graphics.Color
import android.view.Gravity
import android.view.View
import kotlinx.android.synthetic.main.box_dialog_layout.*

/**
 * @author : zhangqi
 * @time : 2020/9/17
 * desc :
 */
open class BoxDialog(context: Context) : BaseDialog(context) {

    init {
        setDialogGravity(Gravity.CENTER)
        setCanceledOnTouchOutside(false)
        setContentView(setLayoutId())
        setDialogWidthPercent(0.75f)
    }

    /**
     * 设置布局Id
     */
    fun setLayoutId() = R.layout.box_dialog_layout

    /**
     * 设置标题
     */
    fun setDialogTitle(title: CharSequence?): BoxDialog {
        title?.let { tvTitle.text = it }
        return this
    }

    fun setTitleColor(titleColor: String?): BoxDialog {
        titleColor?.let {
            tvTitle.setTextColor(Color.parseColor(it))
        }
        return this
    }

    /**
     * 内容
     */
    fun setDialogContent(content: CharSequence?): BoxDialog {
        content?.let { tvContent.text = it }
        return this
    }

    fun setContentColor(contentColor: String?): BoxDialog {
        contentColor?.let { tvContent.setTextColor(Color.parseColor(contentColor)) }
        return this
    }

    /**
     * 确定事件
     */
    fun setPositiveListener(listener: View.OnClickListener): BoxDialog {

        tvPositive.setOnClickListener {
            listener.onClick(it)
        }
        return this
    }

    fun setPositiveText(positiveText: CharSequence?): BoxDialog {
        positiveText?.let {
            tvPositive.text = it
        }

        return this
    }

    fun setPositiveTextColor(colorStr: String?): BoxDialog {
        colorStr?.let {
            tvPositive.setTextColor(Color.parseColor(colorStr))
        }
        return this
    }

    /**
     * 取消事件
     */
    fun setNegativeListener(listener: View.OnClickListener): BoxDialog {
        tvNegative.setOnClickListener {
            listener.onClick(it)
        }
        return this
    }

    fun setNegativeText(negativeText: CharSequence?): BoxDialog {
        negativeText?.let {
            tvNegative.text = it
        }
        return this
    }

    fun setNegativeTextColor(colorStr: String?): BoxDialog {
        colorStr?.let {
            tvNegative.setTextColor(Color.parseColor(colorStr))
        }
        return this
    }
}