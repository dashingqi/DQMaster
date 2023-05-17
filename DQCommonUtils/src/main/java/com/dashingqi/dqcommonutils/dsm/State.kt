package com.dashingqi.dqcommonutils.dsm

/**
 * @desc : 状态机的状态接口
 * @author : zhangqi
 * @time : 2023/5/17 23:03
 */
interface State<in T> {

    fun enter(owner: T)

    fun exit(owner: T)

    fun onMessage(owner: T, msg: Any): Boolean

}