package com.dashingqi.aclass;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.e(TAG, "onCreate: getName == " + MainActivity.class.getName());
        Log.e(TAG, "onCreate:  getSimpleName == " + MainActivity.class.getSimpleName());
        Log.e(TAG, "onCreate: == getCanonicalName == " + MainActivity.class.getCanonicalName());

        Log.e(TAG, "onCreate: inner getName == " + MainActivity.MyClass.class.getName());
        Log.e(TAG, "onCreate: inner getSimpleName == " + MainActivity.MyClass.class.getSimpleName());
        Log.e(TAG, "onCreate: inner getCanonicalName == " + MainActivity.MyClass.class.getCanonicalName());
        /**
         * 运行结果如下
         * 2020-07-05 15:47:21.115 1083-1083/com.dashingqi.aclass E/MainActivity: onCreate: getName == com.dashingqi.aclass.MainActivity
         * 2020-07-05 15:47:21.115 1083-1083/com.dashingqi.aclass E/MainActivity: onCreate:  getSimpleName == MainActivity
         * 2020-07-05 15:47:21.115 1083-1083/com.dashingqi.aclass E/MainActivity: onCreate: == getCanonicalName == com.dashingqi.aclass.MainActivity
         *
         * 2020-07-05 15:47:21.115 1083-1083/com.dashingqi.aclass E/MainActivity: onCreate: inner getName == com.dashingqi.aclass.MainActivity$MyClass
         * 2020-07-05 15:47:21.115 1083-1083/com.dashingqi.aclass E/MainActivity: onCreate: inner getSimpleName == MyClass
         * 2020-07-05 15:47:21.115 1083-1083/com.dashingqi.aclass E/MainActivity: onCreate: inner getCanonicalName == com.dashingqi.aclass.MainActivity.MyClass
         *
         * getSimpleName ---> 就是获取到类的名字
         * getName()和getCanonicalName() 都是获取到包名路径加类名（全类型）
         * 只不过在 针对内部类来说
         * getName() ---> com.dashingqi.aclass.MainActivity$MyClass
         * getCanonicalName() -----> com.dashingqi.aclass.MainActivity.MyClass
         * 一个是$
         * 一个是.
         */

    }

    static class MyClass {

    }
}
