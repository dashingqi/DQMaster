package com.dashingqi.compiler.utils;

/**
 * @ProjectName: DQMaster
 * @Package: com.dashingqi.compiler.utils
 * @ClassName: StringUtils
 * @Author: DashingQI
 * @CreateDate: 2020/6/30 10:56 PM
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/6/30 10:56 PM
 * @UpdateRemark:
 * @Version: 1.0
 */
public class StringUtils {

    //将首字母转为小写
    public static String toLowerCaseFirstChar(String text) {
        if (text == null || text.length() == 0) {
            return "";
        }
        if (Character.isLowerCase(text.charAt(0))) {
            return text;
        }
        return String.valueOf(Character.toLowerCase(text.charAt(0))) + text.substring(1);
    }

    //将首字母转为大写
    public static String toUpperCaseFirstChar(String text) {
        if (Character.isUpperCase(text.charAt(0))) {
            return text;
        } else {
            return String.valueOf(Character.toUpperCase(text.charAt(0))) + text.substring(1);
        }
    }

}
