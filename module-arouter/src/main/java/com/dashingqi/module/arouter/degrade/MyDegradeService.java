package com.dashingqi.module.arouter.degrade;

import android.content.Context;
import android.util.Log;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.facade.service.DegradeService;

/**
 * @ProjectName: DQMaster
 * @Package: com.dashingqi.module.arouter.degrade
 * @ClassName: MyDegradeService
 * @Author: DashingQI
 * @CreateDate: 2020/7/8 11:37 PM
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/7/8 11:37 PM
 * @UpdateRemark:
 * @Version: 1.0
 */
@Route(path = "/degrade/test")
public class MyDegradeService implements DegradeService {

    private static final String TAG = "MyDegradeService";
    @Override
    public void onLost(Context context, Postcard postcard) {
        Log.d(TAG, "onLost: ");

    }

    @Override
    public void init(Context context) {
        Log.d(TAG, "init: ");

    }
}
