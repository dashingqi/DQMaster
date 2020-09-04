package com.dashingqi.dqoverscrolllayout

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.View

/**
 * @author : zhangqi
 * @time : 2020/9/4
 * desc :
 */
class DQOverScrollView : View {

    private var mLeft = 0
    private var mRight = 0
    private var mTop = 0
    private var mBottom = 0

    private val mArcPaint by lazy {
        Paint()
    }

    init {
        mArcPaint.isAntiAlias = true
        mArcPaint.color = Color.parseColor("#DDDDDD")
        mArcPaint.style = Paint.Style.FILL_AND_STROKE
    }

    constructor(context: Context) : super(context)


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawArc(mLeft.toFloat(), mTop.toFloat(), mRight.toFloat(), mBottom.toFloat(), 0f, 360f, false, mArcPaint)
    }

    /**
     * 画弧度
     */
    fun drawArc(left: Int, top: Int, right: Int, bottom: Int) {
        mLeft = left
        mRight = right
        mTop = top
        mBottom = bottom
        invalidate()
    }
}