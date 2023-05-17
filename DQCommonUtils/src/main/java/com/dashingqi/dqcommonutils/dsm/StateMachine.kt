package com.dashingqi.dqcommonutils.dsm

/**
 * @desc : 状态机的实现
 * @author : zhangqi
 * @time : 2023/5/17 23:06
 */
class StateMachine<T>(private var owner: T) {


    /** 当前所处的状态*/
    var mCurrState: State<T>? = null
        private set

    /** 之前所处的状态，用于状态翻转*/
    var mPreState: State<T>? = null
        private set

    /** 全局状态，通用的消息处理由全局状态实现*/
    var mGlobalState: State<T>? = null

    /**
     * 处理消息
     * @param msg Any 消息对象
     * @return Boolean 消息是否被处理
     */
    fun handleMessage(msg: Any): Boolean {
        return when {
            // 当前状态的消息处理
            mCurrState?.onMessage(owner, msg) == true -> {
                true
            }

            // 通用消息的处理
            mGlobalState?.onMessage(owner, msg) == true -> {
                true
            }

            else -> {
                false
            }
        }
    }

    /**
     * 状态的转换
     * @param newState State<T> 新状态
     */
    fun changeState(newState: State<T>) {
        // 退出当前状态
        mPreState = mCurrState
        mCurrState?.exit(owner)

        // 进入新状态
        mCurrState = newState
        mCurrState?.enter(owner)

    }

    /**
     * 状态的反转
     */
    fun revertToPreState() {
        mPreState?.let {
            changeState(it)
        }

    }

}