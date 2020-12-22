package com.dashingqi.dqhttp.call

import android.util.Log
import com.dashingqi.dqhttp.response.IResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * @author : zhangqi
 * @time : 12/22/20
 * desc : 对CallBack的一个预处理
 */
class BaseCallBack<T : IResponse> : Callback<T> {
    override fun onResponse(call: Call<T>, response: Response<T>) {
        Log.d("TAG", "onResponse")
    }

    override fun onFailure(call: Call<T>, t: Throwable) {
        Log.d("TAG", "onFailure")
    }
}