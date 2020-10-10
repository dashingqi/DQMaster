package com.dashingqi.dqlaunchstarter.utils;

import android.util.Log;

/**
 * @author : zhangqi
 * @time : 2020/10/10
 * desc :
 */
public class DispatcherLog {

    private static boolean sDebug = true;

    public static void i(String msg) {
        if (!sDebug) {
            return;
        }
        Log.i("TaskDispatcher",msg);
    }

    public static boolean isDebug() {
        return sDebug;
    }

    public static void setDebug(boolean debug) {
        sDebug = debug;
    }

}