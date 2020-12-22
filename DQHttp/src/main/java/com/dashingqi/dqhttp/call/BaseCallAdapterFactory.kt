package com.dashingqi.dqhttp.call

import com.dashingqi.dqhttp.response.IResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import kotlin.Exception

/**
 * @author : zhangqi
 * @time : 12/22/20
 * desc : 用于支持方法返回值是BaseCallBack的适配器
 *
 * 采用了工厂模式用来 生成CallAdapter
 * CallAdapter中的adapt方法可以获取到Call对象
 * 我们可以在adapt方法中执行 call.enqueue 获取到Response
 *
 */
class BaseCallAdapterFactory : CallAdapter.Factory() {
    override fun get(returnType: Type, annotations: Array<Annotation>, retrofit: Retrofit): CallAdapter<*, *>? {

        //当返回值类型不匹配的情况下，就返回null，Retrofit内部可以拿到下一个适配器
        if (BaseCallBack::class.java != getRawType(returnType)) {
            return null
        }

        if (returnType !is ParameterizedType) {
            return null
        }
        //下面是用来验证返回值范性中包裹的类型 ,也就是我们解析成
        var responseType = getParameterUpperBound(0, returnType)

        var responseClassType = getRawType(responseType)
        if (IResponse::class.java.isAssignableFrom(responseClassType)) {
            //返回我们自定义的CallAdapter
            return ResponseCallAdapter<IResponse>(responseType)
        } else {
            throw Exception("Response must implement IResponse")
        }
    }

    private class ResponseCallAdapter<R : IResponse>(var responseType: Type) : CallAdapter<R, BaseCallBack<R>> {
        override fun responseType(): Type = responseType

        override fun adapt(call: Call<R>): BaseCallBack<R> {

            var baseCallBack = BaseCallBack<R>()

            //这里我们能拿到call对象 我们就可以执行异步的方法，开启一个协程 切换到子线程中

            GlobalScope.launch(Dispatchers.IO) {
                try {
                    var response = call.execute()
                    withContext(Dispatchers.Main) {
                        baseCallBack.onResponse(call, response)
                    }
                } catch (exception: Exception) {
                    withContext(Dispatchers.Main) {
                        baseCallBack.onFailure(call, exception)
                    }
                }
            }
            return baseCallBack
        }
    }
}