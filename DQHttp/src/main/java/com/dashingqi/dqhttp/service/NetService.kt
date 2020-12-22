package com.dashingqi.dqhttp.service

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * @author : zhangqi
 * @time : 12/22/20
 * desc : 网络请求服务的配置
 */
class NetService
//internal关键字限制了跨module的调用
internal constructor(var baseUrl: String?,
                     var ignoreSSl: Boolean,
                     var okHttpClientBuilder: ((OkHttpClient.Builder) -> Unit)?,
                     var retrofitBuilder: ((Retrofit.Builder) -> Unit)?) {


    /**
     * 创建Retrofit
     */
    private fun createRetrofit(): Retrofit {
        var builder = Retrofit.Builder()
        baseUrl?.let {
            builder.baseUrl(baseUrl)
        }
        builder
                //支持RxJava2的转换
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                //支持RxJava3的转换
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                //也得支持一下RxJava3的转换
                .addConverterFactory(GsonConverterFactory.create())
                .client(createOkHttpClient())

        retrofitBuilder?.invoke(builder)


        return builder.build()
    }

    /**
     * 创建OkHttpClient
     *
     */

    private fun createOkHttpClient(): OkHttpClient {
        var okHttpClient = OkHttpClient.Builder()
        if (ignoreSSl) {
            OkHttpHelper.createX509TrustManager().apply {
                okHttpClient.hostnameVerifier(OkHttpHelper.createIgnoreHostnameVerifier())
                        .sslSocketFactory(OkHttpHelper.createIgnoreSSLSocketFactory(this), this)
            }
        }
        okHttpClientBuilder?.invoke(okHttpClient)
        return okHttpClient.build()
    }


    /**
     * 通过动态代理获取到代理对象
     * 拿到代理对象去调用请求方法
     */
    fun <T> create(clazz: Class<T>): T = createRetrofit().create(clazz)

}