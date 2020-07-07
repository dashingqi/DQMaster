package com.dashingqi.module.arouter;

import android.app.Application;

import com.alibaba.android.arouter.BuildConfig;
import com.alibaba.android.arouter.launcher.ARouter;

/**
 * @author : zhangqi
 * @time : 2020/7/7
 * desc :
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            ARouter.openLog();
            ARouter.openDebug();
        }
        //初始化ARouter
        ARouter.init(this);
    }
}
