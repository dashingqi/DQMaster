package com.dashingqi.dqhttp.intercept

import com.dashingqi.dqhttp.wrapper.RequestWrapper
import okhttp3.*
import java.lang.Exception

/**
 * @author : zhangqi
 * @time : 12/18/20
 * desc :
 */
abstract class ParameterIntercept(
        var get: ((RequestWrapper) -> Request)? = null,
        var postFormBody: ((RequestWrapper) -> Request)? = null,
        var postJsonBody: ((RequestWrapper) -> Request)? = null,
        var postMultiPartBody: ((RequestWrapper) -> Request)? = null,
        var postOtherBody: ((RequestWrapper) -> Request)? = null
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()

        val wrapper = RequestWrapper(request)

        if (wrapper.isGet()) {
            get?.let {
                return chain.proceed(it.invoke(wrapper))
            } ?: throw Exception("get not null")

        } else {
            return when (request.body) {
                is FormBody -> {
                    postFormBody?.let {
                        chain.proceed(it.invoke(wrapper))
                    } ?: throw Exception("postFormBody not null")
                }
                is MultipartBody -> {
                    postMultiPartBody?.let {
                        chain.proceed(it.invoke(wrapper))
                    } ?: throw Exception("multipartBody not null")
                }
                else -> {
                    if (wrapper.isJsonBody()) {
                        postJsonBody?.let {
                            chain.proceed(it.invoke(wrapper))
                        } ?: throw Exception("postJsonBody not null")
                    } else {
                        postOtherBody?.let {
                            chain.proceed(it.invoke(wrapper))
                        } ?: chain.proceed(request)
                    }
                }
            }
        }
    }
}