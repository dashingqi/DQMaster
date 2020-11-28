package com.chiatai.module_view.tablayout

import android.content.Context
import android.util.AttributeSet
import androidx.databinding.BindingAdapter
import com.chiatai.module_view.R
import com.google.android.material.tabs.TabLayout

/**
 * @author : zhangqi
 * @time : 11/18/20
 * desc :
 */
class CustomerStyleTabLayout : TabLayout {

    constructor(context: Context) : super(context)
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)
    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int) : super(
            context,
            attributeSet,
            defStyleAttr
    )

    private var mTextSelectedColor: String? = null
    private var mTextDefaultColor: String? = null
    private var mTextDefaultSize: Int = 14
    private var mTextSelectedSize: Int = 14
    private var mTextSelectedIsBold: Boolean = false


    init {

        //
        addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabReselected(tab: Tab?) {

            }

            //未选中的颜色
            override fun onTabUnselected(tab: Tab?) {

            }

            //选中的颜色
            override fun onTabSelected(tab: Tab?) {

            }

        })

        //设置指示器的样式
        setSelectedTabIndicator(R.drawable.base_shape_customer_tl_indicator)
    }

    companion object {

        @JvmStatic
        @BindingAdapter(value = ["exTextSelectedColor", "exTextDefaultColor", "exTextDefaultSize", "exTextSelectedSize", "exTextSelectedIsBold"], requireAll = false)
        fun setTextStyle(view: CustomerStyleTabLayout, textSelectedColor: String, textDefaultColor: String, textDefaultSize: Int, textSelectedSize: Int, textSelectedIsBold: Boolean) {

        }
    }
}