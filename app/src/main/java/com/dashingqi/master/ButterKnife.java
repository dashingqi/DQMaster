package com.dashingqi.master;

import android.app.Activity;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @ProjectName: DQMaster
 * @Package: com.dashingqi.master
 * @ClassName: ButterKnife
 * @Author: DashingQI
 * @CreateDate: 2020/7/1 12:14 AM
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/7/1 12:14 AM
 * @UpdateRemark:
 * @Version: 1.0
 */
public class ButterKnife {

    /**
     * 反射方式调用编译时生成的代码
     * @param activity
     */
    public static void bind(Activity activity){
        Class aClass = activity.getClass();
        try {
            Class<?> viewBinding = Class.forName(aClass.getName()+"ViewBinding");
            Method method = viewBinding.getMethod("bind", aClass);
            method.invoke(viewBinding.newInstance(),activity);
        } catch (ClassNotFoundException | NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
