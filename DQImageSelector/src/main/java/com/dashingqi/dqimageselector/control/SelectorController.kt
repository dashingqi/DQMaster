package com.dashingqi.dqimageselector.control

import android.annotation.SuppressLint
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import com.dashingqi.dqimageselector.activity.ImageSelectorActivity
import com.dashingqi.dqimageselector.listeenr.IControllerView
import com.dashingqi.dqimageselector.model.PhotoItemModel

/**
 * 数据控制层
 * @author : zhangqi
 * @time : 2021/8/15
 * desc :
 */
class SelectorController(
    var controller: IControllerView,
    var loaderManager: LoaderManager,
    var context: Context
) {

    fun fetchData() {
        loaderManager.initLoader(
            LOADER_ALL,
            null,
            object : LoaderManager.LoaderCallbacks<Cursor> {
                override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
                    return CursorLoader(
                        context,
                        MEDIA_STORE_IMAGE_URI, IMAGE_PROJECTION,
                        null, null,
                        IMAGE_PROJECTION[2] + " DESC"
                    )
                }

                @SuppressLint("Range")
                override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
                    data?.let { cursor ->
                        val tempData = mutableListOf<PhotoItemModel>()
                        while (cursor.moveToNext()) {
                            val path =
                                cursor.getString(cursor.getColumnIndex(IMAGE_PROJECTION[0]))
                            val name =
                                cursor.getString(cursor.getColumnIndex(IMAGE_PROJECTION[1]))
                            val date = cursor.getLong(cursor.getColumnIndex(IMAGE_PROJECTION[2]))
                            val id = cursor.getString(cursor.getColumnIndex(IMAGE_PROJECTION[3]))
                            Log.d(ImageSelectorActivity.TAG, "path = $path name = $name date = $date id = $id")
                            val photoItemModel = PhotoItemModel(id, path, name, date)
                            tempData.add(photoItemModel)
                        }
                        controller.onLoadFinish(tempData)
                    }
                }

                override fun onLoaderReset(loader: Loader<Cursor>) {
                }

            })
    }

    companion object {
        /** 查询手机内部图片对应的URI */
        val MEDIA_STORE_IMAGE_URI: Uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI

        /** 查询数据库中的字段*/
        val IMAGE_PROJECTION = arrayOf(
            MediaStore.Images.Media.DATA,
            MediaStore.Images.Media.DISPLAY_NAME,
            MediaStore.Images.Media.DATE_ADDED,
            MediaStore.Images.Media._ID
        )

        const val LOADER_ALL = 10000
    }
}