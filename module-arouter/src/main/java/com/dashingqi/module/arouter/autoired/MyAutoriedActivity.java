package com.dashingqi.module.arouter.autoired;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.dashingqi.module.arouter.R;

@Route(path = "/test/test2")
public class MyAutoriedActivity extends AppCompatActivity {

    private static final String TAG = "MyAutoriedActivity";

    @Autowired
    String name = "";

    @Autowired
    String id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_autoried);
        ARouter.getInstance().inject(this);

        Log.d(TAG, "onCreate: name = " + name);
        Log.d(TAG, "onCreate: id = " + id);

    }
}