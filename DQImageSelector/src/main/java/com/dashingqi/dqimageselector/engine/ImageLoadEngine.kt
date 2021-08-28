package com.dashingqi.dqimageselector.engine

import android.content.Context
import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.ImageView

/**
 * 图片加载引擎接口类
 * @author zhangqi61
 * @since 2021/8/23
 */
interface ImageLoadEngine {

    /**
     * 加载图片
     * @param context Context 上下文环境
     * @param imageView ImageView imageView
     * @param uri Uri 图片的Uri地址
     * @param resizeX Int 宽度
     * @param resizeY Int 高度
     */
    fun loadImage(context: Context, imageView: ImageView, uri: Uri, resizeX: Int, resizeY: Int, placeholder: Drawable?)

    /**
     * 加载gif
     * @param context Context 上下文环境
     * @param imageView ImageView imageView
     * @param uri Uri 图片的Uri地址
     * @param resizeX Int 宽度
     * @param resizeY Int 高度
     */
    fun loadGifImage(
        context: Context,
        imageView: ImageView,
        uri: Uri,
        resizeX: Int,
        resizeY: Int,
        placeholder: Drawable?
    )

    /**
     * 加载裁剪后的图片
     * @param context Context
     * @param imageView ImageView
     * @param uri Uri
     * @param placeholder Drawable
     * @param resize Int
     */
    fun loadCropImage(context: Context, imageView: ImageView, uri: Uri, resize: Int, placeholder: Drawable?)

    /**
     * 加载裁剪后的Gif动图图片
     * @param context Context
     * @param imageView ImageView
     * @param uri Uri
     * @param placeholder Drawable
     * @param resize Int
     */
    fun loadCropGifImage(context: Context, imageView: ImageView, uri: Uri, placeholder: Drawable?, resize: Int)
}