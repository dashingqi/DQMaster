package com.dashingqi.master.process.ipc

import android.os.Bundle
import androidx.annotation.IntDef
import com.dashingqi.master.process.ipc.DelegateResultCode.Companion.CALL_ERROR_CODE
import com.dashingqi.master.process.ipc.DelegateResultCode.Companion.OK_ERROR_CODE
import com.dashingqi.master.process.ipc.DelegateResultCode.Companion.UN_KNOW_ERROR_CODE

/**
 * @desc : 代理执行的结果
 * @author : zhangqi
 * @time : 2023/12/3 11:19
 */
data class DelegateResult(
    /** 结果码*/
    @DelegateResultCode val resultCode: Int,
    /** 结果的Bundle*/
    val resultBundle: Bundle?
)

/** key result code */
internal const val KEY_RESULT_CODE = "key_result_code"


@IntDef(UN_KNOW_ERROR_CODE, CALL_ERROR_CODE, OK_ERROR_CODE)
@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.SOURCE)
internal annotation class DelegateResultCode {
    companion object {

        /** ok code */
        const val OK_ERROR_CODE = 200

        /** un know error code */
        const val UN_KNOW_ERROR_CODE = -1000

        /** call error code */
        const val CALL_ERROR_CODE = -1001
    }
}
