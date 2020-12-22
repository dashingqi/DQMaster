package com.dashingqi.dqhttp.service

import okhttp3.OkHttpClient
import retrofit2.Retrofit

/**
 * @author : zhangqi
 * @time : 12/22/20
 * desc : 采用建造者模式构建NetService
 */
class NetServiceBuilder {

    private var mBaseUrl: String? = null

    //是否忽略ssl
    private var mIsIgnoreSSl: Boolean = false

    private var mOkHttpClientBuilder: ((OkHttpClient.Builder) -> Unit)? = null

    private var mRetrofitBuilder: ((Retrofit.Builder) -> Unit)? = null


    fun setOkHttpClientBuilder(okHttpClientBuilder: (OkHttpClient.Builder) -> Unit): NetServiceBuilder {
        mOkHttpClientBuilder = okHttpClientBuilder
        return this
    }

    fun setRetrofitBuilder(retrofitBuilder: (Retrofit.Builder) -> Unit): NetServiceBuilder {
        mRetrofitBuilder = retrofitBuilder
        return this
    }

    /**
     * 设置访问的域名
     */
    fun setBaseUrl(baseUrl: String): NetServiceBuilder {
        mBaseUrl = baseUrl
        return this
    }

    /**
     * 忽略证书
     */
    fun setIgnoreSSl(isIgnoreSSl: Boolean): NetServiceBuilder {
        mIsIgnoreSSl = isIgnoreSSl
        return this

    }

    fun builder(): NetService {
        return NetService(mBaseUrl, mIsIgnoreSSl, mOkHttpClientBuilder, mRetrofitBuilder)
    }
}