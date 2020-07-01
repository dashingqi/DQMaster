package com.dashingqi.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @ProjectName: DQMaster
 * @Package: com.dashingqi.annotation
 * @ClassName: BindView
 * @Author: DashingQI
 * @CreateDate: 2020/6/30 10:29 PM
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/6/30 10:29 PM
 * @UpdateRemark:
 * @Version: 1.0
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.CLASS)
public @interface BindView {
    int value();
}
