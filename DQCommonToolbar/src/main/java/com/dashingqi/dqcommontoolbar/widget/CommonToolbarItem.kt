package com.dashingqi.dqcommontoolbar.widget

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.dashingqi.dqcommontoolbar.R
import kotlinx.android.synthetic.main.item_common_toolbar_layout.view.*

/**
 * @author : zhangqi
 * @time : 2020/8/25
 * desc : 标题左右布局的通用布局
 */
class CommonToolbarItem : ConstraintLayout {
    var mLeftIcon: ImageView? = null
    var mRightIcon: ImageView? = null
    var mTitleText: TextView? = null

    init {
        var rootView = View.inflate(context, R.layout.item_common_toolbar_layout, this)
        mLeftIcon = rootView.leftIcon
        mRightIcon = rootView.rightIcon
        mTitleText = rootView.title
    }

    constructor(context: Context) : super(context) {

    }

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {

    }

    fun setLeftIcon(leftDrawable: Drawable): CommonToolbarItem {
        mLeftIcon?.visibility = View.VISIBLE
        mLeftIcon?.setImageDrawable(leftDrawable)
        return this
    }

    fun setLeftIcon(leftRes: Int): CommonToolbarItem {
        mLeftIcon?.visibility = View.VISIBLE
        mLeftIcon?.setImageResource(leftRes)
        return this
    }

    fun setLeftTextClickListener(listener: OnClickListener): CommonToolbarItem {
        mTitleText?.visibility = View.VISIBLE
        mTitleText?.setOnClickListener(listener)
        return this
    }


    fun setRightIcon(rightDrawable: Drawable): CommonToolbarItem {
        mRightIcon?.visibility = View.VISIBLE
        mRightIcon?.setImageDrawable(rightDrawable)
        return this
    }

    fun setRightIcon(rightRes: Int): CommonToolbarItem {
        mRightIcon?.visibility = View.VISIBLE
        mRightIcon?.setImageResource(rightRes)
        return this
    }

    fun setRightTextClickListener(listener: OnClickListener): CommonToolbarItem {
        mTitleText?.visibility = View.VISIBLE
        mTitleText?.setOnClickListener(listener)
        return this
    }

    fun setTitleText(title: CharSequence): CommonToolbarItem {
        mTitleText?.visibility = View.VISIBLE
        mTitleText?.text = title
        return this
    }

    fun setTitleTextColor(colorStr: String): CommonToolbarItem {
        mTitleText?.visibility = View.VISIBLE
        mTitleText?.setTextColor(Color.parseColor(colorStr))
        return this
    }

}