package com.dashingqi.dqimageselector.engine.impl

import android.content.Context
import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dashingqi.dqimageselector.engine.ImageLoadEngine

/**
 * Glide图片加载引擎实现类
 * @author zhangqi61
 * @since 2021/8/23
 */
class GlideLoaderEngine : ImageLoadEngine {
    override fun loadImage(context: Context, imageView: ImageView, uri: Uri, resizeX: Int, resizeY: Int) {
        Glide.with(imageView)
            .load(uri)
            .apply(RequestOptions().override(resizeX, resizeY).fitCenter())
            .into(imageView)
    }

    override fun loadGifImage(context: Context, imageView: ImageView, uri: Uri, resizeX: Int, resizeY: Int) {
        Glide.with(imageView)
            .asBitmap()
            .load(uri)
            .apply(RequestOptions().override(resizeX, resizeY).fitCenter())
            .into(imageView)
    }
}