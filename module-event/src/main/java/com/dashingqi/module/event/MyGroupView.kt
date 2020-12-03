package com.dashingqi.module.event

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.ViewGroup

/**
 * @author : zhangqi
 * @time : 11/28/20
 * desc :
 */

class MyGroupView : ViewGroup {
    private val TAG = "MyGroupView"

    constructor(context: Context) : super(context)

    constructor(context: Context, attributes: AttributeSet) : super(context, attributes)

    constructor(context: Context, attributeSet: AttributeSet, defaultRes: Int) : super(context, attributeSet, defaultRes)

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        Log.d(TAG, "dispatchTouchEvent: ")
        return true
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {

    }
}