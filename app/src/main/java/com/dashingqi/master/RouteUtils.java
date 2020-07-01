package com.dashingqi.master;

import android.content.Context;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author : zhangqi
 * @time : 2020/7/1
 * desc :
 */
public class RouteUtils {

    public static RouteUtils instance;

    Map<String, Class> maps = new HashMap();

    public static RouteUtils getInstance() {
        if (instance == null) {
            synchronized (RouteUtils.class) {
                if (instance == null) {
                    instance = new RouteUtils();
                }
            }
        }
        return instance;
    }

    public static void init() {
        try {
            Class<?> aClass = Class.forName("com.dashingqi.master.RouteCompilerImpl");
            Method method = aClass.getMethod("putActivity");
            method.invoke(aClass.newInstance());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

    public void putActivity(String path, Class clz) {
        if (maps == null)
            return;
        maps.put(path, clz);
    }

    public void jumpToActivity(Context context, String path) {
        if (maps.size() > 0) {
            Class clz = (Class) maps.get(path);

        }
    }
}
