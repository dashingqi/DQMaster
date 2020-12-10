package com.dashingqi.dqmvvmbase.activity

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dashingqi.dqmvvmbase.BR
import com.dashingqi.dqmvvmbase.ext.getDbClass
import com.dashingqi.dqmvvmbase.ext.getVmClass

/**
 * @author : zhangqi
 * @time : 2020/9/2
 * desc :
 */
class BaseMVVMActivity<DB : ViewDataBinding, VM : ViewModel> : AppCompatActivity() {

    /**
     * DataBinding
     */
    lateinit var dataBinding: DB

    /**
     * ViewModel
     */
    lateinit var viewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createDataBinding()
        viewModel = createViewModel()
        dataBinding.lifecycleOwner = this
        //绑定ViewModel到布局上
        dataBinding.setVariable(BR.viewModel, viewModel)

    }

    /**
     * 创建DataBinding
     * 设置setContentView
     */
    private fun createDataBinding() {
        var dbClass = getDbClass<DB>(this)
        var method = dbClass.getMethod("inflate", LayoutInflater::class.java)
        dataBinding = method.invoke(null, layoutInflater) as DB
        setContentView(dataBinding.root)
    }

    /**
     * 创建ViewModel
     */
    private fun createViewModel(): VM {
        var vmClass = getVmClass<VM>(this)
        return getViewModelFactory()?.let {
            ViewModelProvider(this, it).get(vmClass)
        } ?: ViewModelProvider(this).get(vmClass)
    }

    /**
     * 如果你需要传递参数到ViewModel中，可以重写该方法
     */
    open fun getViewModelFactory(): ViewModelProvider.Factory? {
        return null
    }

}