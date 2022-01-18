package com.kelin.degreesimageview

import android.content.Context
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.ImageView
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
internal class DegreesController(private val context: Context, val imageView: ImageView) : LifecycleObserver {

    //累积播放的帧数
    internal var accumulate = 0

    /**
     * 手势处理者。
     */
    private val gestureDetector by lazy {
        GestureDetector(context, object : GestureDetector.OnGestureListener {
            override fun onShowPress(e: MotionEvent?) {
            }

            override fun onSingleTapUp(e: MotionEvent?): Boolean {
                return imageView.performClick()
            }

            override fun onDown(e: MotionEvent?): Boolean {
                return true
            }

            override fun onFling(e1: MotionEvent?, e2: MotionEvent?, velocityX: Float, velocityY: Float): Boolean {
                return if (abs(velocityX) >= 500) {
                    accumulate += (velocityX.toInt() / 500)
                    true
                } else {
                    false
                }
            }

            override fun onScroll(e1: MotionEvent?, e2: MotionEvent?, distanceX: Float, distanceY: Float): Boolean {
                val realDistanceX = abs(distanceX)
                when {
                    realDistanceX < 2f -> {
                        accumulate = 0
                    }

                    realDistanceX < 3f -> {
                        if (distanceX > 0) {
                            accumulate = -1
                        } else if (distanceX < 0) {
                            accumulate = 1
                        }
                    }
                    distanceX > 0 -> {
                        if (accumulate < 0) {
                            accumulate = 0
                        } else {
                            accumulate--
                        }
                    }
                    else -> {
                        if (accumulate > 0) {
                            accumulate = 0
                        } else {
                            accumulate++
                        }
                    }
                }
                return true
            }

            override fun onLongPress(e: MotionEvent?) {
            }
        })
    }

    internal fun onTouchEvent(event: MotionEvent?): Boolean {
        event?.also {
            if (it.action == MotionEvent.ACTION_UP || it.action == MotionEvent.ACTION_CANCEL) {
                accumulate = 0
            }
        }
        imageView.parent?.requestDisallowInterceptTouchEvent(true)
        return gestureDetector.onTouchEvent(event)
    }
}