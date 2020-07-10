package com.dashingqi.module.arouter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.callback.NavigationCallback;
import com.alibaba.android.arouter.launcher.ARouter;

/**
 * ARouter中的拦截器
 *
 * @Interceptor(priority = 5)
 * priority值越小说明它的优先级越高，在进行页面跳转的时候，就先被执行到
 * _ARouter.afterInit()
 */

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private Button mBtnJump;
    private Button mBtnDegrade;
    private Button mBtnGlobalDegrade;
    private Button mBtnTestAutoired;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBtnJump = findViewById(R.id.btnJump);
        mBtnJump.setOnClickListener(v -> ARouter.getInstance().build("/interceptor/test").navigation());

        mBtnDegrade = findViewById(R.id.btnDegrade);
        mBtnDegrade.setOnClickListener(view -> {
            ARouter.getInstance().build("/test/test").navigation(this, new NavigationCallback() {
                @Override
                public void onFound(Postcard postcard) {
                    Log.d(TAG, "onFound: ");

                }

                /**
                 * 出现问题会回调该方法
                 * 也就是我们降级策略的处理回调
                 * @param postcard
                 */
                @Override
                public void onLost(Postcard postcard) {
                    Log.d(TAG, "onLost: ");

                }

                @Override
                public void onArrival(Postcard postcard) {
                    Log.d(TAG, "onArrival: ");

                }

                @Override
                public void onInterrupt(Postcard postcard) {
                    Log.d(TAG, "onInterrupt: ");

                }
            });

        });

        mBtnGlobalDegrade = findViewById(R.id.btnGlobalDegrade);
        mBtnGlobalDegrade.setOnClickListener(view -> {
            ARouter.getInstance().build("/test1/test1").navigation();
        });

        mBtnTestAutoired = findViewById(R.id.btnTestAutoired);
        mBtnTestAutoired.setOnClickListener(view -> {
            ARouter.getInstance().build("/test/test2")
                    .withString("id", "1922321321")
                    .withString("name", "dashingqi")
                    .navigation();
        });
    }
}
