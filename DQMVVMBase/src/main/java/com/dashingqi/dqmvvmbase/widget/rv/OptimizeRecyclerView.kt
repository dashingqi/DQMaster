package com.dashingqi.dqmvvmbase.widget.rv

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.abs

/**
 * @author : zhangqi
 * @time : 12/5/20
 * desc : 优化后的RecyclerView
 */
class OptimizeRecyclerView : RecyclerView {

    private var mOrientation: Int = 1

    init {
        itemAnimator = null
        overScrollMode = OVER_SCROLL_NEVER

        if (layoutManager is LinearLayoutManager) {
            mOrientation = (layoutManager as LinearLayoutManager).orientation
        }
    }


    constructor(context: Context) : super(context)

    constructor(context: Context, set: AttributeSet) : super(context, set)

    constructor(context: Context, set: AttributeSet, defStyleAttr: Int) : super(
            context,
            set,
            defStyleAttr
    )

    var mStartX = 0.0f
    var mStartY = 0.0f


    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        ev?.let { motionEvent ->
            when (ev.action) {
                MotionEvent.ACTION_DOWN -> {
                    mStartX = motionEvent.x
                    mStartY = motionEvent.y
                    //告诉ViewGroup不要去拦截
                    parent.requestDisallowInterceptTouchEvent(true)
                }
                MotionEvent.ACTION_MOVE -> {
                    var endX = motionEvent.x
                    var endY = motionEvent.y
                    var delayX = abs(endX - mStartX)
                    var delayY = abs(endY - mStartY)
                    if (mOrientation == VERTICAL) {
                        if (delayX > delayY * 3) {
                            //告诉parent去拦截事件
                            parent.requestDisallowInterceptTouchEvent(false)
                        } else {
                            parent.requestDisallowInterceptTouchEvent(true)
                        }
                    } else if (mOrientation == HORIZONTAL) {
                        if (delayY * 0.4 > delayX) {
                            parent.requestDisallowInterceptTouchEvent(false)
                        } else {
                            parent.requestDisallowInterceptTouchEvent(true)
                        }
                    }
                }
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    parent.requestDisallowInterceptTouchEvent(true)
                }
            }
        }
        return super.dispatchTouchEvent(ev)
    }
}