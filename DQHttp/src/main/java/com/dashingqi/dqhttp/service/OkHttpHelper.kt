package com.dashingqi.dqhttp.service

import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

/**
 * @author : zhangqi
 * @time : 12/22/20
 * desc : 证书忽略的配置
 */
object OkHttpHelper {
    fun createIgnoreSSLSocketFactory(x509TrustManager: X509TrustManager): SSLSocketFactory {
        val ssl = SSLContext.getInstance("SSL")
        ssl.init(null, arrayOf<TrustManager>(x509TrustManager), SecureRandom())
        return ssl.socketFactory
    }

    fun createX509TrustManager(): X509TrustManager {
        return object : X509TrustManager {
            override fun checkClientTrusted(p0: Array<out X509Certificate>?, p1: String?) {

            }

            override fun checkServerTrusted(p0: Array<out X509Certificate>?, p1: String?) {

            }

            override fun getAcceptedIssuers(): Array<X509Certificate> {
                return arrayOf()
            }
        }
    }

     fun createIgnoreHostnameVerifier() = HostnameVerifier { p0, p1 -> true }
}