package com.dashingqi.dqhttp.call

import android.util.Log
import com.dashingqi.dqhttp.response.IResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

/**
 * @author : zhangqi
 * @time : 12/22/20
 * desc : 对CallBack的一个预处理
 * 这里仅仅做请求的分发，业务的分发在LiveDataCallBack中做
 */
open class BaseCallBack<T : IResponse> : Callback<T> {
    override fun onResponse(call: Call<T>, response: Response<T>) {
        Log.d("TAG", "onResponse")
        try {
            if (response.code() != 200) {
                responseCodeErrorActions.forEach {
                    it.invoke(call)
                }
                return
            }

            val body = response.body()
            if (body == null) {
                failContainerAction.forEach {
                    it.invoke(call)
                }
                return
            }
            when {
                body.isSuccess() -> {
                    //成功的回调action执行
                    successContainerAction.forEach {
                        it.invoke(call, body)
                    }
                }
                //token 出现问题的回调
                body.isTokenError() -> {
                    //这个地方是不是需要提供一个回调出去
                }
                //服务器返回错误action
                body.isCodeError() -> {
                    responseCodeErrorActions.forEach {
                        it.invoke(call)
                    }
                }
                //其他情况
                else -> {
                    failContainerAction.forEach {
                        it.invoke(call)
                    }
                }
            }
        } catch (exception: Exception) {
            onFailure(call, exception)
        }
    }

    override fun onFailure(call: Call<T>, t: Throwable) {
        failContainerAction.forEach {
            it.invoke(call)
        }
    }

    /**
     * 成功回调的action
     */
    private val successContainerAction = ArrayList<(call: Call<T>, response: T) -> Unit>()
    open fun doOnResponseSuccess(action: (call: Call<T>, response: T) -> Unit): BaseCallBack<T> {
        successContainerAction.add(action)
        return this
    }

    /**
     * 成功回调后执行的第一个action
     */
    open fun doOnResponseHeaderSuccess(action: (call: Call<T>, response: T) -> Unit): BaseCallBack<T> {
        successContainerAction.add(0, action)
        return this
    }


    /**
     * 失败回调的action
     */
    private val responseCodeErrorActions = ArrayList<(call: Call<T>) -> Unit>()
    open fun doOnResponseCodeError(action: (call: Call<T>) -> Unit): BaseCallBack<T> {
        responseCodeErrorActions.add(action)
        return this
    }

    /**
     * 任何失败的回调处理
     */
    private val failContainerAction = ArrayList<(call: Call<T>) -> Unit>()
    open fun doOnFailure(action: (call: Call<T>) -> Unit): BaseCallBack<T> {
        failContainerAction += action
        return this
    }
}