package com.dashingqi.dqimageselector.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.provider.MediaStore
import android.util.Log

/**
 * @author : zhangqi
 * @time : 2021/8/7
 * desc : 媒体文件的工具类
 */
object MediaStoreUtil {
    /** TAG */
    private const val TAG = "MediaStoreUtil"

    /**
     * 获取到手机相册中KV数据
     */
    @SuppressLint("Range")
    fun getImagekV(owner: Activity) {
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
}