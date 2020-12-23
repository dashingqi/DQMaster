package com.dashingqi.dqhttp.call

import com.dashingqi.dqhttp.response.IResponse

/**
 * @author : zhangqi
 * @time : 12/23/20
 * desc : 业务层面的CallBack
 *  通常我们都会用这个来处理业务的回调
 *
 */
class BusinessCallBack<T : IResponse> : BaseCallBack<T>() {

    init {
        /**
         * code错误
         */
        doOnResponseCodeError {

        }

        /**
         * 其他错误
         */
        doOnFailure {

        }

        /**
         * 成功的回调处理
         */
        doOnResponseSuccess { call, response ->

        }
    }


    /**
     * 绑定一个Dialog
     */
    fun bindDialog(): BusinessCallBack<T> {
        return this
    }

    /**
     * 绑定一个请求状态页面
     */
    fun bindStateLayout(): BusinessCallBack<T> {
        return this
    }
}