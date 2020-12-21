package com.dashingqi.dqhttp.intercept

import com.dashingqi.dqhttp.wrapper.RequestWrapper
import okhttp3.*
import java.lang.Exception

/**
 * @author : zhangqi
 * @time : 12/18/20
 * desc : 根据请求方式和body来做区分，针对不同的请求给予不同的回调方法
 */
abstract class ParameterIntercept : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()

        val wrapper = RequestWrapper(request)

        if (wrapper.isGet()) {
            return chain.proceed(get(wrapper))

        } else {
            return when (request.body) {
                is FormBody -> {
                    chain.proceed(postFormBody(wrapper))
                }
                is MultipartBody -> {
                    chain.proceed(postMultiPartBody(wrapper))
                }
                else -> {
                    if (wrapper.isJsonBody()) {
                        chain.proceed(postJsonBody(wrapper))
                    } else {
                        chain.proceed(postOtherBody(wrapper))
                    }
                }
            }
        }
    }

    abstract fun get(requestWrapper: RequestWrapper): Request
    abstract fun postFormBody(requestWrapper: RequestWrapper): Request
    abstract fun postJsonBody(requestWrapper: RequestWrapper): Request
    abstract fun postMultiPartBody(requestWrapper: RequestWrapper): Request
    abstract fun postOtherBody(requestWrapper: RequestWrapper): Request
}