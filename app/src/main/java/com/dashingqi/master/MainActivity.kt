package com.dashingqi.master

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.dashingqi.dqimageselector.build.SelectorBuild
import com.dashingqi.master.databinding.ActivityMainBinding
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
            SelectorBuild.builder().start(this, IMAGE_SELECT_REQUEST_CODE)
        }

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }

    companion object {
        /** 吊起选择图片页面Activity的请求码*/
        const val IMAGE_SELECT_REQUEST_CODE = 10001
    }
}