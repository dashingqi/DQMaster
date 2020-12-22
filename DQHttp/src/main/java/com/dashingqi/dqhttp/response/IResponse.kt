package com.dashingqi.dqhttp.response

/**
 * @author : zhangqi
 * @time : 12/22/20
 * desc : 对于BaseResponse需要实现IResponse
 */
interface IResponse {
    fun isTokenError(): Boolean
    fun isSuccess(): Boolean
    fun getMessage(): String
}