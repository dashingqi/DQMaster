package com.dashingqi.master.process.ipc

import android.content.Context
import android.os.Bundle

/**
 * @desc : 对外提供代理 Provider 抽象类
 * @author : zhangqi
 * @time : 2023/12/3 11:06
 */
abstract class AbsDelegateProvider {
    abstract fun executeCall(context: Context?, params: Bundle?): Bundle
}