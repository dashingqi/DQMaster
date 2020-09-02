package com.dashingqi.dqmvvmbase.ext

import java.lang.reflect.ParameterizedType

/**
 * @author : zhangqi
 * @time : 2020/9/2
 * desc :
 */

fun <VM> getVmClass(obj: Any): Class<VM> {
    return (obj.javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[1] as Class<VM>
}

fun <DB> getDbClass(obj: Any): Class<DB> {
    return (obj.javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<DB>
}