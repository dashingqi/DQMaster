package com.dashingqi.dqmvvmbase.async

import android.os.AsyncTask

/**
 * @author : zhangqi
 * @time : 12/13/20
 * desc :
 * T --> params
 * P --> Progress
 * R ---> Result
 * 自定义AsyncTask
 * 通过高阶函数转换为回调的方法来进行接耦
 *
 * AsyncTask对象需要在UI线程中创建
 * execute方法需要在UI线程中调用 ，一个AsyncTask对象只能调用一次这个方法
 *
 */
class CustomAsyncTask<T, P, R>(
    private val onPreExecute: (() -> Unit)? = null,
    private val doInBackground: ((Array<out T>) -> R)? = null,
    private val onProgressUpdate: ((Array<out P>) -> Unit)? = null,
    private val onPostExecute: ((R) -> Unit)? = null
) : AsyncTask<T, P, R>() {
    override fun onPreExecute() {
        onPreExecute?.invoke()
    }

    override fun doInBackground(vararg params: T): R? {
        return doInBackground?.invoke(params)
    }

    override fun onProgressUpdate(vararg values: P) {
        onProgressUpdate?.invoke(values)
    }

    override fun onPostExecute(result: R) {
        onPostExecute?.invoke(result)
    }
}