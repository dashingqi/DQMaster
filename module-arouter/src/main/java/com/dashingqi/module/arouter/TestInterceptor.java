package com.dashingqi.module.arouter;

import android.content.Context;
import android.util.Log;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Interceptor;
import com.alibaba.android.arouter.facade.callback.InterceptorCallback;
import com.alibaba.android.arouter.facade.template.IInterceptor;

/**
 * @author : zhangqi
 * @time : 2020/7/7
 * desc :
 */
@Interceptor(priority = 5)
public class TestInterceptor implements IInterceptor {
    private static final String TAG = "TestInterceptor";

    @Override
    public void process(Postcard postcard, InterceptorCallback callback) {
        Log.d(TAG, "process: ----> perform");
        if (postcard.getPath().equals("/interceptor/test")) {
            Log.d(TAG, "TestInterceptor ---> /interceptor/test ");
            callback.onContinue(postcard);
        } else {
            callback.onInterrupt(null);
        }
    }

    @Override
    public void init(Context context) {
        Log.d(TAG, "init: ----> perform");

    }
}
