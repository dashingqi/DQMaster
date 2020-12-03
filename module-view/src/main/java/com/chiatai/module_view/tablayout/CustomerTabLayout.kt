package com.chiatai.module_view.tablayout

import android.content.Context
import android.util.AttributeSet
import com.chiatai.module_view.R
import com.google.android.material.tabs.TabLayout

/**
 * @author : zhangqi
 * @time : 11/18/20
 * desc :
 */
class CustomerTabLayout:TabLayout {

    constructor(context: Context) : super(context)
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)
    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int) : super(
            context,
            attributeSet,
            defStyleAttr
    )

    init {

        //设置指示器的样式
        setSelectedTabIndicator(R.drawable.base_shape_customer_tl_indicator)
    }
}