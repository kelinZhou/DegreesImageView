package com.kelin.degreesimageview

import android.graphics.drawable.Drawable
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import java.io.File

/**
 * **描述:** 默认的图片加载器。
 *
 * **创建人:** kelin
 *
 * **创建时间:** 2022/1/14 1:27 PM
 *
 * **版本:** v 1.0.0
 */
class DefaultImageLoader : ImageLoader {

    override fun loadImages(target: ImageView, res: List<String>, onDone: (successful: Boolean) -> Unit) {
        if (res.isNotEmpty()) {
            loadInto(target, res.first(), null, true)//加载第一帧。
            onLoadImages(target, res.subList(1, res.size), onDone)//加载其他帧
        }
    }

    override fun loadInto(target: ImageView, url: String, placeholder: Drawable?, cache: Boolean) {
        Glide.with(target).load(url).let {
            if (cache) {
                it.diskCacheStrategy(DiskCacheStrategy.DATA)
            } else {
                it
            }
        }.placeholder(placeholder).into(target)
    }


    private fun onLoadImages(target: ImageView, res: List<String>, onDone: (successful: Boolean) -> Unit) {
        ImageRequestListener(res, onDone).also { listener ->
            res.forEach { loadImageByUrlAsFile(target, it, listener) }
        }
    }

    /**
     * 创建下载任务。
     */
    private fun loadImageByUrlAsFile(target: ImageView, url: String, listener: RequestListener<File>) {
        Glide.with(target).asFile()
            .load(url)
            .diskCacheStrategy(DiskCacheStrategy.DATA)
            .listener(listener)
            .submit()
    }

    private inner class ImageRequestListener(private val res: List<String>, private val onLoadFinishListener: (successful: Boolean) -> Unit) : RequestListener<File> {

        private val handler by lazy { Handler(Looper.getMainLooper()) }

        private var successCount = 0

        private var finishedCount = 0

        override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<File>?, isFirstResource: Boolean): Boolean {
            checkFinished()
            return false
        }

        private fun checkFinished() {
            if (++finishedCount == res.size) {
                handler.post { onLoadFinishListener(successCount > res.size / 2) }
            }
        }

        override fun onResourceReady(resource: File?, model: Any?, target: Target<File>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
            ++successCount
            checkFinished()
            return false
        }
    }
}