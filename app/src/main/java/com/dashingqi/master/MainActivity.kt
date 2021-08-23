package com.dashingqi.master

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.dashingqi.dqimageselector.build.SelectorBuild
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
            SelectorBuild
                .builder()
                .setMaxSelectSize(12)
                .isShowCamera(true)
                .start(this, IMAGE_SELECT_REQUEST_CODE)
        }
        openMatisse.setOnClickListener {
            openMatisse()
        }

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun openMatisse(){
        Matisse.from(this@MainActivity)
            .choose(MimeType.ofAll())
            .countable(true)
            .maxSelectable(9)
            .thumbnailScale(0.85f)
            .imageEngine(GlideEngine())
            .forResult(1000)
    }

    companion object {
        /** 吊起选择图片页面Activity的请求码*/
        const val IMAGE_SELECT_REQUEST_CODE = 10001
    }
}