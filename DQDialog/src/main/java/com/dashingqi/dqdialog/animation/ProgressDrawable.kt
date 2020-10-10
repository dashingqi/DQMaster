package com.dashingqi.dqdialog.animation

import android.animation.Animator
import android.animation.ValueAnimator
import android.animation.ValueAnimator.AnimatorUpdateListener
import android.graphics.Canvas
import android.graphics.Path
import android.graphics.drawable.Animatable
import android.graphics.drawable.Drawable
import androidx.annotation.NonNull

/**
 * @author : zhangqi
 * @time : 2020/9/16
 * desc : 旋转菊花
 */
open class ProgressDrawable : PaintDrawable(), Animatable, AnimatorUpdateListener {
    protected var mWidth = 0
    protected var mHeight = 0
    protected var mProgressDegree = 0
    protected var mValueAnimator: ValueAnimator
    protected var mPath = Path()
    override fun onAnimationUpdate(animation: ValueAnimator) {
        val value = animation.animatedValue as Int
        mProgressDegree = 30 * (value / 30)
        val drawable: Drawable = this@ProgressDrawable
        drawable.invalidateSelf()
    }

    //<editor-fold desc="Drawable">
    override fun draw(@NonNull canvas: Canvas) {
        val drawable: Drawable = this@ProgressDrawable
        val bounds = drawable.bounds
        val width = bounds.width()
        val height = bounds.height()
        val r = Math.max(1f, width / 22f)
        if (mWidth != width || mHeight != height) {
            mPath.reset()
            mPath.addCircle(width - r, height / 2f, r, Path.Direction.CW)
            mPath.addRect(width - 5 * r, height / 2f - r, width - r, height / 2f + r, Path.Direction.CW)
            mPath.addCircle(width - 5 * r, height / 2f, r, Path.Direction.CW)
            mWidth = width
            mHeight = height
        }
        canvas.save()
        canvas.rotate(mProgressDegree.toFloat(), width / 2f, height / 2f)
        for (i in 0..11) {
            mPaint.alpha = (i + 5) * 0x11
            canvas.rotate(30f, width / 2f, height / 2f)
            canvas.drawPath(mPath, mPaint)
        }
        canvas.restore()
    }

    //</editor-fold>
    override fun start() {
        if (!mValueAnimator.isRunning) {
            mValueAnimator.addUpdateListener(this)
            mValueAnimator.start()
        }
    }

    override fun stop() {
        if (mValueAnimator.isRunning) {
            val animator: Animator = mValueAnimator
            animator.removeAllListeners()
            mValueAnimator.removeAllUpdateListeners()
            mValueAnimator.cancel()
        }
    }

    override fun isRunning(): Boolean {
        return mValueAnimator.isRunning
    }

    init {
        mValueAnimator = ValueAnimator.ofInt(30, 3600)
        mValueAnimator.duration = 10000
        mValueAnimator.interpolator = null
        mValueAnimator.repeatCount = ValueAnimator.INFINITE
        mValueAnimator.repeatMode = ValueAnimator.RESTART
    }
}