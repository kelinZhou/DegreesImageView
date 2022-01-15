package com.kelin.degreesimageview

import android.graphics.drawable.Drawable
import android.widget.ImageView

/**
 * **描述:** 图片获取器。
 *
 * **创建人:** kelin
 *
 * **创建时间:** 2022/1/14 1:24 PM
 *
 * **版本:** v 1.0.0
 */
interface ImageLoader {

    /**
     * 加载所有图片，调用该方法会将第一张图片加载到目标View上并预加载其他的图片。
     * @param target 目标图片组件。
     * @param res 图片资源。
     * @param onDone 加载完成的回调。
     */
    fun loadImages(target: ImageView, res: List<String>, onDone: (successful: Boolean) -> Unit)

    /**
     * 加载图片到目标View上。
     * @param target 目标图片组件。
     * @param url 图片地址。
     * @param placeholder 占位图。
     * @param cache 是否缓存本次图片。
     */
    fun loadInto(target: ImageView, url: String, placeholder: Drawable? = null, cache: Boolean = false)
}