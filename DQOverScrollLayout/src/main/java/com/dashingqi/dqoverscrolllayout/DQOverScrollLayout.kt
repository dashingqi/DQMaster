package com.dashingqi.dqoverscrolllayout

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.animation.TranslateAnimation
import android.widget.RelativeLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.abs

/**
 * @author : zhangqi
 * @time : 2020/9/4
 * desc :
 * 1. 往左滑动，需要弹出一个弧度的View，需要一个ArcView
 * 2. 弧度View中有文本，需要一个TextView
 * 上述的1，2点都是手动加入的 下面的Rv是通过包裹的xml
 * 3. 列表是一个RecyclerView
 */
class DQOverScrollLayout : RelativeLayout {
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)

    /**
     * 列表View
     */
    lateinit var mRvView: RecyclerView

    /**
     * 弧度的View
     */
    lateinit var mOverScrollView: DQOverScrollView

    /**
     * 弧度的最大宽度
     */
    private val mMaxArcWidth = 100

    /**
     * DOWN 事件时的 x坐标
     */
    private var mDownX = 0.0f

    /**
     * 阻尼
     */
    private val DAMP = .3f

    /**
     * 用来保存rv的位置·
     */
    private var rvOriginRect = Rect()

    /**
     * 是否移动
     */
    private var isMoved = false

    /**
     * RecyclerView接受事件
     */
    private var isRecyclerReceiveEvent = false


    private val ANOTATION_DURATION: Long = 1000

    override fun onFinishInflate() {
        super.onFinishInflate()
        mOverScrollView = DQOverScrollView(this.context)
        addView(mOverScrollView)
    }

    /**
     * 测量
     */
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        for (position in 0..childCount) {
            if (getChildAt(position) is RecyclerView) {
                mRvView = getChildAt(position) as RecyclerView
            }
        }

        //测量RecyclerView
        val rvWidthMeasureSpec = MeasureSpec.makeMeasureSpec(measuredWidth - paddingLeft - paddingRight, MeasureSpec.AT_MOST)
        val rvHeightMeasureSpec = MeasureSpec.makeMeasureSpec(measuredHeight - paddingTop - paddingBottom, MeasureSpec.AT_MOST)
        mRvView.measure(rvWidthMeasureSpec, rvHeightMeasureSpec)

        //测量View
        val scrollViewWidthSpec = MeasureSpec.makeMeasureSpec(mMaxArcWidth, MeasureSpec.AT_MOST)
        val scrollViewHeightSpec = MeasureSpec.makeMeasureSpec(measuredHeight - paddingTop - paddingBottom, MeasureSpec.AT_MOST)
        mOverScrollView.measure(scrollViewWidthSpec, scrollViewHeightSpec)

    }

    /**
     * 布局
     */
    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        val rvMeasureWidth = mRvView.measuredWidth
        val rvMeasureHeight = mRvView.measuredHeight
        mRvView.layout(l, t, l + rvMeasureWidth, t + rvMeasureHeight)

        val mOverScrollViewMeasureWidth = mOverScrollView.measuredWidth
        mOverScrollView.layout(r - mOverScrollViewMeasureWidth, t, r, b)

        /**
         * 保存一下Rv初始位置
         */
        rvOriginRect.set(l, t, l + rvMeasureWidth, t + rvMeasureHeight)

    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        //不让父控件拦截事件
        requestDisallowInterceptTouchEvent(true)
        when (ev?.action) {
            /**
             * 记录一下 按下的位置
             */
            MotionEvent.ACTION_DOWN -> {
                //按下
                mDownX = ev.x
            }
            /**
             * 在移动的过程中，需要知道什么时候要展示出OverScrollView
             * 这个过程就是获取到Rv当前可见范围的最后一个完整View的position是childCount-1,并且是向左滑动的 （moveX<mDownX ）
             */
            MotionEvent.ACTION_MOVE -> {
                //移动
                val moveX = ev.x
                var tempX = (moveX - mDownX) * DAMP
                if (isCanShowArc() && tempX < 0) {
                    val absTempX = abs(tempX)
                    //布局Rv
                    mRvView.layout((rvOriginRect.left - absTempX).toInt(), rvOriginRect.top, (rvOriginRect.right - absTempX).toInt(), rvOriginRect.bottom)
                    if (absTempX < mMaxArcWidth) {
                        mOverScrollView.drawArc((mMaxArcWidth - absTempX).toInt(), rvOriginRect.top, (mMaxArcWidth + absTempX).toInt(), rvOriginRect.bottom)
                    }
                    isMoved = true
                    isRecyclerReceiveEvent = false
                    overScrollViewToOrigin()
                    return true

                } else {
                    mDownX = ev.x
                    isMoved = false
                    isRecyclerReceiveEvent = true
                    overScrollViewToOrigin()
                    return super.dispatchTouchEvent(ev)
                }
            }

            MotionEvent.ACTION_UP -> {
                //抬起来
                if (isMoved) {
                    overScrollViewToOrigin()
                }
                return if (isRecyclerReceiveEvent) {
                    super.dispatchTouchEvent(ev)
                } else {
                    true
                }
            }
        }

        return super.dispatchTouchEvent(ev)

    }

    /**
     * 用于判断是否要展示右侧的弧度布局
     */
    private fun isCanShowArc(): Boolean {

        var adapter = mRvView.adapter as RecyclerView.Adapter

        //获取到最有一个Item的角标
        val lastItemPosition = adapter.itemCount - 1

        //获取到布局管理器
        val rvManager = mRvView.layoutManager as LinearLayoutManager

        //获取到可见区域内最后一个完整的Item的position
        var findLastVisibleItemPosition = rvManager.findLastVisibleItemPosition()

        if (lastItemPosition == findLastVisibleItemPosition) {
            return true
        }
        return false
    }

    /**
     * overScrollView恢复到原位
     */
    private fun overScrollViewToOrigin() {
        if (!isMoved) {
            return
        }
        //移动Rv
        val rvTranslation = TranslateAnimation((mRvView.left - rvOriginRect.left).toFloat(), 0f, 0f, 0f)
        rvTranslation.duration = ANOTATION_DURATION
        mRvView.startAnimation(rvTranslation)
        mRvView.layout(rvOriginRect.left, rvOriginRect.top, rvOriginRect.right, rvOriginRect.bottom)

        //恢复OverScrollView，更具Duration的事件来更新

        var overScrollViewAnimation = ValueAnimator.ofInt(mMaxArcWidth - mOverScrollView.left, 0)
        overScrollViewAnimation.duration = ANOTATION_DURATION
        overScrollViewAnimation.addUpdateListener {
            var offsetValue = it.animatedValue as Int
            //更新OverView的位置
            mOverScrollView.drawArc(mMaxArcWidth - offsetValue, rvOriginRect.top, mMaxArcWidth + offsetValue, rvOriginRect.bottom)
        }

        overScrollViewAnimation.start()

    }
}