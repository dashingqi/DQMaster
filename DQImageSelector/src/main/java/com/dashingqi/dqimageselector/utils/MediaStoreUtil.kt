package com.dashingqi.dqimageselector.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import java.io.File

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
}