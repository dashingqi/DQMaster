package com.dashingqi.module.arouter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.alibaba.android.arouter.launcher.ARouter;

/**
 * ARouter中的拦截器
 * @Interceptor(priority = 5)
 * priority值越小说明它的优先级越高，在进行页面跳转的时候，就先被执行到
 * _ARouter.afterInit()
 */

public class MainActivity extends AppCompatActivity {

    private Button mBtnJump;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBtnJump = findViewById(R.id.btnJump);
        mBtnJump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build("/interceptor/test").navigation();
            }
        });
    }
}
