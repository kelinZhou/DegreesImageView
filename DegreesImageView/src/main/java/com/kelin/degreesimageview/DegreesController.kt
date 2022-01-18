package com.kelin.degreesimageview

import android.animation.ValueAnimator
import android.content.Context
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.animation.DecelerateInterpolator
import androidx.lifecycle.LifecycleObserver
import kotlin.math.abs

/**
 * **描述:** 360图片控制器。
 *
 * **创建人:** kelin
 *
 * **创建时间:** 2022/1/14 1:05 PM
 *
 * **版本:** v 1.0.0
 */
internal class DegreesController(private val context: Context, val target: DegreesImageView) : GestureDetector.OnGestureListener, LifecycleObserver {

    /**
     * 阻力，阻力值越大滑动越不灵敏。
     */
    internal var resistance = 3

    /**
     * 阻力的双倍。
     */
    private val resistanceD: Int
        get() = resistance.shl(1)

    /**
     * 计算阻力值的临时变量。
     */
    private var distanceXTotal = 0F

    /**
     * 手势处理者。
     */
    private val gestureDetector by lazy {
        GestureDetector(context, this)
    }

    internal fun onTouchEvent(event: MotionEvent?): Boolean {
        return gestureDetector.onTouchEvent(event)
    }

    override fun onShowPress(e: MotionEvent?) {
    }

    override fun onSingleTapUp(e: MotionEvent?): Boolean {
        return target.performClick()
    }

    override fun onDown(e: MotionEvent?): Boolean {
        return true
    }

    override fun onFling(e1: MotionEvent?, e2: MotionEvent?, velocityX: Float, velocityY: Float): Boolean {
        ValueAnimator.ofInt((velocityX / 2000).toInt(), 0).apply {
            duration = (600 * (abs(velocityX) / 10000)).toLong()
            interpolator = DecelerateInterpolator()
            addUpdateListener { va ->
                (va.animatedValue as Int).takeIf { it != 0 }?.also { value ->
                    val targetValue = abs(value).let { if (it > 3) 3 else it }
                    target.onSwitchNext(value > 0, targetValue)
                }
            }
            start()
        }
        return true
    }

    override fun onScroll(e1: MotionEvent?, e2: MotionEvent?, distanceX: Float, distanceY: Float): Boolean {
        distanceXTotal += abs(distanceX)
        if (distanceXTotal > resistance) {
            target.onSwitchNext(distanceX < 0, distanceXTotal.let { if (it > resistanceD) resistanceD else it.toInt() } / resistance)
            distanceXTotal = 0F
        }
        return true
    }

    override fun onLongPress(e: MotionEvent?) {
    }
}