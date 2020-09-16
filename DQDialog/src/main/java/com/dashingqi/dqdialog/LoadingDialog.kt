package com.dashingqi.dqdialog

import android.content.Context
import android.os.Bundle
import com.dashingqi.dqdialog.animation.WhiteLoadingDrawable
import kotlinx.android.synthetic.main.loading_layout.*

/**
 * @author : zhangqi
 * @time : 2020/9/16
 * desc : 加载框 一般用于网络提交数据的等待加载框
 */
class LoadingDialog : BaseDialog {
    constructor(context: Context) : super(context, R.style.base_loadingDialogTheme)

    init {
        //设置布局
        setContentView(R.layout.loading_layout)
        setCanceledOnTouchOutside(false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //设置成白色的菊花转圈
        loadingPB.indeterminateDrawable = WhiteLoadingDrawable()
    }
}