package com.kelin.degreesimageview

import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import io.reactivex.Completable
import io.reactivex.CompletableObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

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

    private var onLoadFinishListener: ((successful: Boolean) -> Unit)? = null

    private val disposableHelper = CompositeDisposable()

    override fun loadImages(target: ImageView, res: List<String>, onDone: (successful: Boolean) -> Unit) {
        if (res.isNotEmpty()) {
            onLoadFinishListener = onDone
            loadInto(target, res.first(), null, true)//加载第一帧。
            onLoadImages(target, res.subList(1, res.size))//加载其他帧
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


    private fun onLoadImages(target: ImageView, res: List<String>) {
        Completable.merge(res.map { createCompletable(target, it) })
            .subscribeOn(Schedulers.io())
            .unsubscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : CompletableObserver {
                override fun onSubscribe(d: Disposable) {
                    disposableHelper.add(d)
                }

                override fun onComplete() {
                    onLoadFinishListener?.invoke(true)
                }

                override fun onError(e: Throwable) {
                    onLoadFinishListener?.invoke(false)
                    e.printStackTrace()
                }
            })

    }

    /**
     * 创建下载任务。
     */
    private fun createCompletable(target: ImageView, url: String): Completable {
        return Completable.create {
            Glide.with(target).asFile()
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .submit()
            it.onComplete()
        }
    }
}