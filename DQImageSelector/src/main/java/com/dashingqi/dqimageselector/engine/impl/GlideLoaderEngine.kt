package com.dashingqi.dqimageselector.engine.impl

import android.content.Context
import android.graphics.drawable.Drawable
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

    /**
     * 加载图片
     * @param context Context 上下文环境
     * @param imageView ImageView
     * @param uri Uri 图片的Uri
     * @param resizeX Int 调整的宽度
     * @param resizeY Int 调整的高度
     */
    override fun loadImage(
        context: Context,
        imageView: ImageView,
        uri: Uri,
        resizeX: Int,
        resizeY: Int,
        placeHolder: Drawable?
    ) {
        Glide.with(imageView)
            .load(uri)
            .apply(RequestOptions().override(resizeX, resizeY).fitCenter())
            .placeholder(placeHolder)
            .into(imageView)
    }

    /**
     * 加载Gif
     * @param context Context 上下文
     * @param imageView ImageView
     * @param uri Uri 图片的Uri
     * @param resizeX Int 调整的宽度
     * @param resizeY Int 调整的高度
     */
    override fun loadGifImage(
        context: Context,
        imageView: ImageView,
        uri: Uri,
        resizeX: Int,
        resizeY: Int,
        placeHolder: Drawable?
    ) {
        Glide.with(imageView)
            .asBitmap()
            .load(uri)
            .apply(RequestOptions().override(resizeX, resizeY).fitCenter())
            .placeholder(placeHolder)
            .into(imageView)
    }

    /**
     * 加载裁剪后的图片
     * @param context Context 上下文环境
     * @param imageView ImageView
     * @param uri Uri 图片的Uri
     * @param placeHolder Drawable 默认的占位图
     * @param resize Int 调整的大小
     */
    override fun loadCropImage(context: Context, imageView: ImageView, uri: Uri, resize: Int, placeHolder: Drawable?) {
        Glide.with(imageView)
            .load(uri)
            .apply(RequestOptions().override(resize, resize).centerCrop())
            .placeholder(placeHolder)
            .into(imageView)
    }

    /**
     * 加载裁剪后的Gif图
     * @param context Context 上下文环境
     * @param imageView ImageView
     * @param uri Uri 图片的Uri
     * @param resize Int 调整的大小
     * @param placeHolder Drawable 占位图
     */
    override fun loadCropGifImage(
        context: Context,
        imageView: ImageView,
        uri: Uri,
        placeHolder: Drawable?,
        resize: Int
    ) {
        Glide.with(imageView)
            .asBitmap()
            .load(uri)
            .apply(RequestOptions().override(resize, resize).centerCrop())
            .placeholder(placeHolder)
            .into(imageView)
    }
}