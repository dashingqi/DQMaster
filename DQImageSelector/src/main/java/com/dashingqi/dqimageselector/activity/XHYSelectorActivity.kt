package com.dashingqi.dqimageselector.activity

import android.Manifest
import android.content.pm.PackageManager
import android.database.Cursor
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.loader.app.LoaderManager
import androidx.loader.content.Loader
import com.dashingqi.dqimageselector.R
import com.dashingqi.dqimageselector.adapter.ImageSelectorAdapter
import com.dashingqi.dqimageselector.control.SelectorController
import com.dashingqi.dqimageselector.databinding.ActivityImageSelectorBinding
import com.dashingqi.dqimageselector.listeenr.IControllerView
import com.dashingqi.dqimageselector.listeenr.IPhotoItemListener
import com.dashingqi.dqimageselector.model.PhotoItemModel

/**
 * 图片选择页面
 */
class XHYSelectorActivity : AppCompatActivity(), IPhotoItemListener, IControllerView {

    /** LoaderManager Instance */
    private val mLoaderManager by lazy {
        LoaderManager.getInstance(this)
    }

    /** SelectorControl */
    private var mSelectorController: SelectorController? = null

    /** adapter */
    private lateinit var adapter: ImageSelectorAdapter

    /** ActivityImageSelectorBinding */
    private val binding by lazy {
        ActivityImageSelectorBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        adapter = ImageSelectorAdapter(binding.recyclerView,this)
        adapter.setItemListener(this)
        // 设置分割线
        //binding.recyclerView.addItemDecoration(SelectorItemDecoration(LINE_COUNT))
        binding.recyclerView.adapter = adapter
        mSelectorController = SelectorController(this, mLoaderManager, this)
        // 预览设置点击事件
        binding.tvPreview.setOnClickListener {
            ImagePreviewActivity.startPreviewActivity(
                this,
                ImagePreviewActivity.PREVIEW_VIEW_REQUEST_CODE,
                null,
                adapter.mSelectedItems,
                adapter.mSelectedItems
            )
        }
        val checkResult = ContextCompat.checkSelfPermission(this, WRITE_PERMISSION) ==
                PackageManager.PERMISSION_GRANTED
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkResult) {
            mSelectorController?.fetchData()
        } else {
            mSelectorController?.fetchData()
        }
    }

    /**
     * Item点击事件的回调
     */
    override fun onItemClick(position: Int, photoItem: PhotoItemModel?) {
        photoItem?.let {
            ImagePreviewActivity.startPreviewActivity(
                this,
                ImagePreviewActivity.PREVIEW_VIEW_REQUEST_CODE,
                it,
                adapter.mData,
                adapter.mSelectedItems
            )
        }
    }

    /**
     * 选择按钮点击事件的回调
     */
    override fun onSelectClick(position: Int, photoItem: PhotoItemModel?) {
        adapter.notifyItemChanged(position, ImageSelectorAdapter.NOTIFY_REFRESH_SELECT_CODE)
    }

    /**
     * 更新编辑按钮
     */
    override fun updateEditView() {
        updateViewState(adapter.mSelectedItems.size)
    }

    /**
     * 用于更新预览和完成按钮的状态
     */
    private fun updateViewState(selectorSize: Int) {
        val viewState = selectorSize > 0
        // 预览按钮
        binding.tvPreview.apply {
            isSelected = viewState
            isEnabled = viewState
        }
        // 完成按钮
        binding.btnFinish.apply {
            isSelected = viewState
            isEnabled = viewState
            text = if (viewState) {
                resources.getString(R.string.finish_text) +
                        resources.getString(R.string.finish_number, selectorSize)
            } else {
                resources.getString(R.string.finish_text)
            }
        }
    }

    override fun onPreCreateLoader(id: Int) {
    }

    /**
     * 从数据查询数据还通过该方法回调给Activity
     * @param data MutableList<PhotoItemModel> 手机中图片集合
     */
    override fun onLoadFinish(data: ArrayList<PhotoItemModel>) {
        // 对于新增的图片，暂时不考虑更新到列表中 我看微信是这样搞的！！！这个地方没想好怎么搞
        if (data.isNotEmpty() && adapter.mData.isEmpty()) {
            runOnUiThread {
                adapter.setData(data)
            }
        }
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {
    }


    companion object {

        /** 写权限*/
        const val WRITE_PERMISSION = Manifest.permission.WRITE_EXTERNAL_STORAGE

        /** RecyclerView每一行展示的子View的个数*/
        const val LINE_COUNT = 4

        /** TAG */
        const val TAG = "ImageSelectorActivity"

    }
}