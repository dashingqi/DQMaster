package com.dashingqi.master;

import android.app.Application;

/**
 * @author : zhangqi
 * @time : 2020/7/1
 * desc :
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化路由
        RouteUtils.init();

    }
}
