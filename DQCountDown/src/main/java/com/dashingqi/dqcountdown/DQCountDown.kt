package com.dashingqi.dqcountdown

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.dashingqi.dqcountdown.bean.TimeBean
import com.dashingqi.dqcountdown.utils.TimeUtils
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import java.util.concurrent.TimeUnit

/**
 * @author : zhangqi
 * @time : 12/4/20
 * desc :
 */
open class DQCountDown : RelativeLayout {

    private val TAG = "DQCountDown"

    var countDownTime: String = "0"

    var disposable: Disposable? = null

    private var mRootView: View? = null

    var timeBean = TimeBean()


    private lateinit var mTvHour: TextView
    private lateinit var mTvMinute: TextView
    private lateinit var mTvSecond: TextView

    private var mCountDownListener: CountDownListener? = null


    init {

        mRootView =
                LayoutInflater.from(context).inflate(getLayoutId(), null, true)
    }


    constructor(context: Context) : this(context, null)

    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0)
    constructor(context: Context, attributeSet: AttributeSet?, resource: Int) : super(
            context,
            attributeSet,
            resource
    ) {

        addView(mRootView)
        mRootView?.let { view ->
            mTvHour = view.findViewById(R.id.tvHour)
            mTvMinute = view.findViewById(R.id.tvMinus)
            mTvSecond = view.findViewById(R.id.tvSecond)

        }
    }

    private fun handleView() {
        val tempCountDownTime = countDownTime.toLong()
        val timeLong = tempCountDownTime + System.currentTimeMillis()
        disposable?.dispose()
        if (countDownTime != "0") {
            Observable.interval(0, 1000, TimeUnit.MILLISECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            object : Observer<Long> {
                                override fun onComplete() {
                                }

                                override fun onSubscribe(d: Disposable) {
                                    disposable = d
                                }

                                override fun onNext(t: Long) {
                                    var tempTimeLong = timeLong - System.currentTimeMillis()
                                    if (tempTimeLong <= 0) {
                                        setTime(TimeBean())
                                        mCountDownListener?.countDownStop()
                                        disposable?.dispose()
                                    } else {
                                        setTime(TimeUtils.formatTimeLong(timeLong,timeBean))
                                    }
                                }

                                override fun onError(e: Throwable) {
                                }
                            })
        }

    }

    /**
     * 处理时间
     */
    private fun setTime(timeBean: TimeBean) {
        mTvHour.text = timeBean.hour
        mTvMinute.text = timeBean.minute
        mTvSecond.text = timeBean.second
    }

    /**
     * 注册事件
     */
    fun registerCountDownListener(listener: CountDownListener) {

        mCountDownListener = listener
    }

    interface CountDownListener {
        /**
         * 计时器停止时候的回调
         */
        fun countDownStop()
    }


    /**
     * 从页面中退出的时候，需要暂停这个计时器
     */
    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        disposable?.dispose()
    }

    /**
     * 任何一个控件的封装需要考虑全面一些，
     * 这个attach 在 removeView() 然后在addView()的时候就会触发这个方法
     * 所以需要在这个方法做处理，不然定时器就跑不起来了
     */
    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        handleView()

    }

    companion object {
        @JvmStatic
        @BindingAdapter(value = ["exCountDownTime"])
        fun exCountDownTime(view: DQCountDown, exCountDownTime: String?) {
            view.countDownTime = exCountDownTime ?: "0"
            view.mRootView?.let {
                view.handleView()
            } ?: Log.e(view.TAG, "获取布局文件失败")

        }
    }

    /**
     * 对外提供用于设置倒计时布局的
     */
    open fun getLayoutId(): Int {
        return R.layout.dq_count_down_layout
    }
}