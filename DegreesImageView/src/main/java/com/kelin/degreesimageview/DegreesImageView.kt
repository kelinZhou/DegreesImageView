package com.kelin.degreesimageview

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
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
     * 用来记录资源是否加载成功。
     */
    private var isSwitcherRunning = false

    var imageLoader: ImageLoader = DefaultImageLoader()

    /**
     * 用来记录当前图片的索引。
     */
    private var currentIndex = 0

    private val controller = DegreesController(context, this)

    /**
     * 图片切换任务。
     */
    private val imageSwitcher by lazy {
        object : Runnable {
            override fun run() {
                if (controller.accumulate > 0) {
                    controller.accumulate--
                    showNext()
                } else if (controller.accumulate < 0) {
                    controller.accumulate++
                    showLast()
                }
                handler?.postDelayed(this, 40)
            }
        }
    }

    /**
     * 显示上一张图片。
     */
    private fun showLast() {
        currentIndex--
        if (currentIndex < 0) {
            currentIndex = imageResources.lastIndex
        }
        imageLoader.loadInto(this, imageResources[currentIndex], drawable)
    }

    /**
     * 显示下一张图片。
     */
    private fun showNext() {
        currentIndex++
        if (currentIndex >= imageResources.size) {
            currentIndex = 0
        }
        imageLoader.loadInto(this, imageResources[currentIndex], drawable)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        return controller.onTouchEvent(event)
    }

    /**
     * 设置图片资源。
     * @param res 图片资源。
     * @param howManyFramesAutoShow 当图片加载完成后自动播放的帧数，如果加载完成不希望播放则传0，如果希望顺时针播放一遍则传入负的图片数量，如果希望逆时针播放一遍这传入正的图片数量，默认顺时针播放一遍。
     */
    fun setImageResources(res: List<String>, howManyFramesAutoShow: Int = -res.size) {
        isSwitcherRunning = false
        imageResources.clear()
        imageResources.addAll(res)
        imageLoader.loadImages(this, imageResources) { successful ->
            if (successful) {
                if (howManyFramesAutoShow != 0) {
                    controller.accumulate = howManyFramesAutoShow
                }
                runSwitcher()
            }
        }
    }

    override fun onVisibilityChanged(changedView: View, visibility: Int) {
        super.onVisibilityChanged(changedView, visibility)
        if (visibility == View.VISIBLE) {
            //这里延迟300毫秒，是因为页面进入动画通常为300毫秒，是尽可能保证在页面可见后再播放旋转。
            handler?.postDelayed({ runSwitcher() }, 300)
        } else {
            isSwitcherRunning = false
            handler?.removeCallbacks(imageSwitcher)
        }
    }


    /**
     * 当资源预加载完毕后调用。
     */
    private fun runSwitcher() {
        if (isAttachedToWindow && !isSwitcherRunning) {
            isSwitcherRunning = true
            handler?.post(imageSwitcher)
        }
    }
}