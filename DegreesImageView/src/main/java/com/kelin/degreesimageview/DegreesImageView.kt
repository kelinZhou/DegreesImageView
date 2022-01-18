package com.kelin.degreesimageview

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatImageView

/**
 * **描述:** 360图片组件。
 *
 * **创建人:** kelin
 *
 * **创建时间:** 2022/1/14 1:02 PM
 *
 * **版本:** v 1.0.0
 */
class DegreesImageView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : AppCompatImageView(context, attrs, defStyleAttr) {

    /**
     * 用来存放图片资源。
     */
    private val imageResources = ArrayList<String>(36)

    /**
     * 用来记录是否反转手势。
     */
    private var isReversal = false

    /**
     * 当加载完成后自动播放多少帧。
     */
    private var framesAutoShow = 0

    /**
     * 图片加载器。
     */
    var imageLoader: ImageLoader = DefaultImageLoader()

    /**
     * 用来记录当前图片的索引。
     */
    private var currentIndex = 0

    private val controller = DegreesController(context, this)

    /**
     * 显示下一张图片。
     * @param leftToRight 是否为从左往右滑动。
     */
    internal fun onSwitchNext(leftToRight: Boolean, count: Int) {
        val realAction = if (isReversal) !leftToRight else leftToRight
        if (realAction) {
            currentIndex -= count
            if (currentIndex < 0) {
                currentIndex = imageResources.lastIndex
            }
        } else {
            currentIndex += count
            if (currentIndex >= imageResources.size) {
                currentIndex %= imageResources.size
            }
        }
        imageLoader.loadInto(this, imageResources[currentIndex], drawable)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        parent?.requestDisallowInterceptTouchEvent(true)
        return controller.onTouchEvent(event)
    }

    /**
     * 设置图片资源。
     * @param res 图片资源。
     * @param reversal 是否反转手势处理，由于资源顺序的不同，所以这里提供该参数用户反转手势。
     * @param resistance 阻力，阻力值越大滑动越不灵敏。
     * @param howManyFramesAutoShow 当图片加载完成后自动播放的帧数，如果加载完成不希望播放则传0，如果希望播放则传入要播放的帧数(允许范围为：0~res.lastIndex)。
     */
    fun setImageResources(res: List<String>, reversal: Boolean = false, resistance: Int = 3, howManyFramesAutoShow: Int = res.lastIndex) {
        isReversal = reversal
        framesAutoShow = if (howManyFramesAutoShow < 0) 0 else if (howManyFramesAutoShow > res.lastIndex) res.lastIndex else howManyFramesAutoShow
        controller.resistance = if (resistance < 2) 2 else resistance
        imageResources.clear()
        imageResources.addAll(res)
        imageLoader.loadImages(this, imageResources) { successful ->
            if (successful) {
                if (framesAutoShow != 0) {
                    switchTo(framesAutoShow, 400)
                }
            }
        }
    }

    /**
     * 自动旋转到指定位置。
     * @param position 要旋转到的位置。
     * @param duration 希望旋转的时长。
     */
    fun switchTo(position: Int, duration: Long) {
        val start = if (isReversal) {
            position
        } else {
            currentIndex
        }
        val end = if (isReversal) {
            currentIndex
        } else {
            position
        }
        ValueAnimator.ofInt(start, end).apply {
            this.duration = duration
            addUpdateListener {
                imageLoader.loadInto(this@DegreesImageView, imageResources[it.animatedValue as Int], drawable)
            }
            startDelay = 200
            start()
        }
    }
}