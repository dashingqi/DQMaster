package com.dashingqi.dqhttp.wrapper

import okhttp3.*
import okio.Buffer
import okio.BufferedSink

/**
 * @author : zhangqi
 * @time : 12/18/20
 * desc :
 */
class RequestWrapper(var request: Request) {

    /**
     * 是否是GET请i去
     */
    fun isGet(): Boolean = request.method.equals("GET", true)

    /**
     * 是否是POST请求
     */
    fun isPost(): Boolean = request.method.equals("POST", true)

    /**
     * 是否是Form表单的请求
     */
    fun isFormBody(): Boolean = request.body is FormBody

    /**
     * 是否是Json Body
     *
     */
    fun isJsonBody(): Boolean = request.body?.contentType()?.subtype == "json"

    /**
     * part body
     */
    fun isMultiPartBody(): Boolean = request.body is MultipartBody

    /**
     * 拿到header中的参数
     */
    fun getHeadersParams(): HashMap<String, String> {
        val map = HashMap<String, String>()
        request.headers.forEach {
            map[it.first] = it.second
        }
        return map
    }

    /**
     * 拿到Form表单中的请求参数与值
     */
    fun getFormBodyParams(): HashMap<String, String> {
        val map = HashMap<String, String>()
        val formBody = request.body as FormBody
        for (index in 0 until formBody.size) {
            map[formBody.name(index)] = formBody.value(index)
        }
        return map
    }

    /**
     * 拿到get请求中拼接的参数
     */
    fun getUrlParams(): HashMap<String, String> {
        val map = HashMap<String, String>()
        request.url.queryParameterNames.forEach { key ->
            var value = request.url.queryParameter(key)
            if (value != null) {
                map[key] = value
            }
        }
        return map
    }

    /**
     * 将传递的Body转换成String字符串
     */
    fun getJsonBodyToString(): String {
        val buffer = Buffer()
        request.body?.writeTo(buffer)
        return buffer.readByteString().utf8()
    }

    /**
     * 设置头部参数
     */
    fun Request.Builder.setHeader(headers: Map<String, String>): Request.Builder {
        headers.forEach {
            this.addHeader(it.key, it.value)
        }
        return this
    }

    /**
     * 为FormBody添加参数
     */
    fun Request.Builder.setFormBody(formBody: Map<String, String>): Request.Builder {
        val builder = FormBody.Builder()
        formBody.forEach {
            builder.add(it.key, it.value)
        }
        var newRequestFormBody = builder.build()
        this.method(request.method, newRequestFormBody)
        return this
    }

    /**
     * 设置JsonBody
     */
    fun Request.Builder.setJsonBody(jsonBody: String): Request.Builder {
        val requestBody = object : RequestBody() {
            override fun contentType(): MediaType? {
                return request.body?.contentType()
            }

            override fun writeTo(sink: BufferedSink) {
                sink.buffer.writeUtf8(jsonBody)
            }
        }
        this.method(request.method, requestBody)
        return this
    }

    /**
     * 获取到地址的字符串
     */
    fun getUrl(): String = request.url.toString()

    /**
     * 获取到HttpUrl
     */
    fun getHttpUrl(): HttpUrl = request.url

    /**
     * 拿到一个新的request builder
     */
    fun setNewRequestBuilder(): Request.Builder {
        return request.newBuilder()
    }
}