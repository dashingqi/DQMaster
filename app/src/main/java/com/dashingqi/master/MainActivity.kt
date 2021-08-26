package com.dashingqi.master

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.dashingqi.dqimageselector.activity.XHYSelectorActivity
import com.dashingqi.dqimageselector.build.SelectorBuild
import com.dashingqi.dqimageselector.xhy.XHY
import com.dashingqi.master.databinding.ActivityMainBinding
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import com.zhihu.matisse.engine.impl.GlideEngine
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var activityMainBinding = DataBindingUtil
            .setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        var viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        activityMainBinding.viewModel = viewModel
        activityMainBinding.lifecycleOwner = this
        viewModel.countDownTime.value = "1232938293"
        tvText.setOnClickListener {
            handlePermission()
        }
        openMatisse.setOnClickListener {
            openMatisse()
        }

    }

    /**
     * 处理权限
     * 当获取到权限就去手机中查询数据
     * 没有获取到权限就去申请权限
     */
    private fun handlePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, WRITE_PERMISSION)
                != PackageManager.PERMISSION_GRANTED
            ) {
                // 没有获取到权限在做出判断
                if (ActivityCompat
                        .shouldShowRequestPermissionRationale(this, WRITE_PERMISSION)
                ) {
                    // 弹出对话框让用户去设置页面进行开启

                } else {
                    //做权限的申请
                    ActivityCompat
                        .requestPermissions(
                            this, arrayOf(WRITE_PERMISSION),
                            REQUEST_PERMISSION_CODE
                        )
                }
            } else {
                openXHY()
            }
        } else {
            openXHY()
        }
    }

    /**
     * 权限申请结果处理
     */
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_PERMISSION_CODE -> {
                takeIf { grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED }.apply {
                    openXHY()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun openMatisse() {
        Matisse.from(this@MainActivity)
            .choose(MimeType.ofAll())
            .countable(true)
            .maxSelectable(9)
            .thumbnailScale(0.85f)
            .imageEngine(GlideEngine())
            .forResult(1000)
    }

    private fun openXHY() {
        XHY.with(this)
            .loadMimeType()
            .capture(true)
            .setMaxSize(9)
            .startForResult(1000)

    }

    companion object {
        /** 吊起选择图片页面Activity的请求码*/
        const val IMAGE_SELECT_REQUEST_CODE = 10001

        /** 请求权限的code */
        const val REQUEST_PERMISSION_CODE = 10001

        /** 写权限*/
        const val WRITE_PERMISSION = Manifest.permission.WRITE_EXTERNAL_STORAGE
    }
}