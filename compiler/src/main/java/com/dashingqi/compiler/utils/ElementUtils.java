package com.dashingqi.compiler.utils;

import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

/**
 * @ProjectName: DQMaster
 * @Package: com.dashingqi.compiler.utils
 * @ClassName: ElementUtils
 * @Author: DashingQI
 * @CreateDate: 2020/6/30 10:56 PM
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/6/30 10:56 PM
 * @UpdateRemark:
 * @Version: 1.0
 */
public class ElementUtils {

    //获取包名
    public static String getPackageName(Elements elementUtils, TypeElement typeElement) {
        return elementUtils.getPackageOf(typeElement).getQualifiedName().toString();
    }

    //获取顶层类类名
    public static String getEnclosingClassName(TypeElement typeElement) {
        return typeElement.getSimpleName().toString();
    }

    //获取静态内部类类名
    public static String getStaticClassName(TypeElement typeElement) {
        return getEnclosingClassName(typeElement) + "Holder";
    }

}