package com.dashingqi.dqhttp.intercept

import com.dashingqi.dqhttp.wrapper.RequestWrapper
import okhttp3.*
import java.lang.Exception

/**
 * @author : zhangqi
 * @time : 12/18/20
 * desc :
 */
class ParameterIntercept(
        var get: ((RequestWrapper) -> Request)? = null,
        var postFormBody: ((RequestWrapper) -> Request)? = null,
        var postJsonBody: ((RequestWrapper) -> Request)? = null,
        var multiPartBody: ((RequestWrapper) -> Request)? = null
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()

        val wrapper = RequestWrapper(request)

        if (wrapper.isGet()) {
            return chain.proceed(request)
        } else {
            return when (request.body) {
                is FormBody -> {
                    postFormBody?.let {
                        chain.proceed(it.invoke(wrapper))
                    } ?: throw Exception("postFormBody not null")
                }
                is MultipartBody -> {
                    multiPartBody?.let {
                        chain.proceed(it.invoke(wrapper))
                    } ?: throw Exception("multipartBody not null")
                }
                else -> {
                    if (wrapper.isJsonBody()) {
                        postJsonBody?.let {
                            chain.proceed(it.invoke(wrapper))
                        } ?: throw Exception("postJsonBody not null")
                    } else {
                        chain.proceed(request)
                    }
                }
            }
        }
    }
}