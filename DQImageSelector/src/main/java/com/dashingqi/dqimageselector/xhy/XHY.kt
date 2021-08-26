package com.dashingqi.dqimageselector.xhy

import android.app.Activity
import android.net.Uri
import androidx.fragment.app.Fragment
import com.dashingqi.dqimageselector.enum.MimeTypeEnum
import com.dashingqi.dqimageselector.selection.SelectionCreator
import java.lang.ref.WeakReference

/**
 * xhy
 * @author : zhangqi
 * @time : 2021/8/24 00:51
 */
class XHY {

    /** Activity的弱引用 */
    private var mContext: WeakReference<Activity>? = null

    /** Fragment的弱引用*/
    private var mFragment: WeakReference<Fragment>? = null

    private constructor(activity: Activity) {
        mContext = WeakReference(activity)
    }

    private constructor(fragment: Fragment) {
        mContext = WeakReference(fragment.activity)
        mFragment = WeakReference(fragment)
    }

    /**
     * 设置选择的媒体类别信息
     * @param mimeTypes Set<MimeTypeEnum> 媒体类别
     */
    fun loadMimeType(mimeTypes: Set<MimeTypeEnum> = MimeTypeEnum.ofAll()): SelectionCreator {
        return SelectionCreator(this, mimeTypes)
    }

    /**
     * 获取到Activity
     * @return Activity?
     */
    fun getActivity(): Activity? {
        return mContext?.get()
    }

    /**
     * 获取到Fragment
     * @return Fragment?
     */
    fun getFragment(): Fragment? {
        return mFragment?.get()
    }

    companion object {
        /**
         * Activity中调用
         * @param activity Activity
         */
        fun with(activity: Activity): XHY {
            return XHY(activity)
        }

        /**
         * Fragment中调用
         * @param fragment Fragment
         */
        fun with(fragment: Fragment): XHY {
            return XHY(fragment)
        }

        /**
         * 获取到数据路径的集合
         * @return List<String>
         */
        fun getPathResult(): List<String> {
            return mutableListOf()
        }

        /**
         * 获取到数据Uri的集合
         * @return List<Uri>
         */
        fun getUriResult(): List<Uri> {
            return mutableListOf()
        }
    }

}