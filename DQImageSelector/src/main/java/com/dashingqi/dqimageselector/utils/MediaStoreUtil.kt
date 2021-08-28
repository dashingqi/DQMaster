package com.dashingqi.dqimageselector.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Point
import android.hardware.display.DisplayManager
import android.net.Uri
import android.provider.MediaStore
import android.util.DisplayMetrics
import android.util.Log
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException
import java.io.InputStream

/**
 * 媒体文件的工具类
 * @author : zhangqi
 * @time : 2021/8/7
 */
object MediaStoreUtil {
    /** TAG */
    private const val TAG = "MediaStoreUtil"

    /** 手机图片的Uri*/
    private const val CONTENT_IMAGE = "content://media/external/images/media"

    /** Uri 拼接符*/
    private const val CONTENT_CONCAT = "=? "

    /** 最大图片的宽度*/
    private const val MAX_WIDTH = 1600

    /**
     * 获取到手机相册中KV数据
     */
    @SuppressLint("Range")
    fun getImageKV(owner: Activity) {
        val currentPhoneImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        var imageCursor = owner.contentResolver.query(
            currentPhoneImageUri,
            null,
            null,
            null,
            null,
            null
        )
        imageCursor?.let { cursor ->
            var columnNames = cursor.columnNames
            while (cursor.moveToNext()) {
                Log.d(TAG, "---------------------")
                for (columnName: String in columnNames) {
                    Log.d(
                        TAG, "name ---> $columnName value ---> " +
                                "${cursor.getString(cursor.getColumnIndex(columnName))} "
                    )
                }
                Log.d(TAG, "---------------------")
            }

            cursor.close()
        }
    }

    /**
     * 将图片地址转换成Uri
     */
    @SuppressLint("Range")
    fun convertPathToUri(context: Context, imgPath: String): Uri? {
        val cursor = context.contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            arrayOf(MediaStore.Images.Media._ID), "${MediaStore.Images.Media.DATA}${CONTENT_CONCAT}",
            arrayOf(imgPath), null
        )
        return if (cursor != null && cursor.moveToFirst()) {
            val id = cursor.getInt(cursor.getColumnIndex(MediaStore.MediaColumns._ID))
            val tempUri = Uri.parse(CONTENT_IMAGE)
            Uri.withAppendedPath(tempUri, "$id")
        } else {
            if (File(imgPath).exists()) {
                val values = ContentValues()
                values.put(MediaStore.Images.Media.DATA, imgPath)
                context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
            } else {
                null
            }
        }
    }

    /**
     * 获取到Bitmap的size
     * @param uri Uri
     * @param activity Activity
     */
    fun getBitmapSize(uri: Uri, activity: Activity): Point {
        val contentResolver = activity.contentResolver
        val imagePoint = getBitmapBounds(contentResolver, uri)
        val w = imagePoint.x
        val h = imagePoint.y
        if (h == 0)return Point(MAX_WIDTH,MAX_WIDTH)
        val displayMetrics = DisplayMetrics()
        activity.windowManager.defaultDisplay.getMetrics(displayMetrics)
        val screenWidth = displayMetrics.widthPixels
        val screenHeight = displayMetrics.heightPixels
        val widthScale = screenWidth/w
        val heightScale = screenHeight/h
        return Point(w*widthScale,h*heightScale)
    }

    /**
     * 通过uri获取到图片的width 和height
     * @param resolver ContentResolver
     * @param uri Uri 图片文件的Uri地址
     * @return Point 包裹宽和高
     */
    fun getBitmapBounds(resolver: ContentResolver, uri: Uri): Point {
        var inputStream: InputStream? = null
        try {
            val options = BitmapFactory.Options()
            options.inJustDecodeBounds = true
            inputStream = resolver.openInputStream(uri)
            BitmapFactory.decodeStream(inputStream, null, options)
            val width = options.outWidth
            val height = options.outHeight
            return Point(width, height)
        } catch (e: FileNotFoundException) {
            return Point(0, 0)
        } finally {
            inputStream?.let {
                try {
                    it.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }
}