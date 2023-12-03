package com.dashingqi.master.process.ipc

import android.os.Bundle
import com.dashingqi.master.process.ipc.DelegateResultCode.Companion.CALL_ERROR_CODE
import com.dashingqi.master.process.ipc.DelegateResultCode.Companion.OK_ERROR_CODE

/**
 * @desc : IPC 跨进程代理的 Provider
 * @author : zhangqi
 * @time : 2023/12/3 11:02
 */
open class IpcDelegateProvider : SimpleContentProvider() {

    override fun call(method: String, arg: String?, extras: Bundle?): Bundle? {
        val delegateBundle = Bundle()
        val delegateProvider = createDelegateProviderInstance(method) ?: return delegateBundle.apply {
            putInt(KEY_RESULT_CODE, CALL_ERROR_CODE)
        }
        val resultBundle = delegateProvider.executeCall(context, extras)
        return delegateBundle.apply {
            putInt(KEY_RESULT_CODE, OK_ERROR_CODE)
            putBundle(KEY_RESULT_BUNDLE, resultBundle)
        }
    }

    private fun createDelegateProviderInstance(delegateClassName: String): AbsDelegateProvider? {
        kotlin.runCatching {
            val delegateClass = Class.forName(delegateClassName)
            delegateClass ?: return null
            val declaredConstructor = delegateClass.getDeclaredConstructor()
            declaredConstructor.isAccessible = true
            val newInstance = declaredConstructor.newInstance()
            if (newInstance !is AbsDelegateProvider) {
                return null
            }
            return newInstance
        }.onFailure {
            it.message.logD()
        }
        return null
    }

    companion object {
        /** key result bundle */
        internal const val KEY_RESULT_BUNDLE = "keyResultBundle"
    }
}