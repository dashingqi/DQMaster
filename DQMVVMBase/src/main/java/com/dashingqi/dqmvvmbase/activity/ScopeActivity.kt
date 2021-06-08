package com.dashingqi.dqmvvmbase.activity

import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel

/**
 * 自带协程作用域以及取消协程的Activity【ScopeActivity】
 * @author zhangqi
 * @since 6/8/21
 */
abstract class ScopeActivity : AppCompatActivity(), CoroutineScope by MainScope() {
    override fun onDestroy() {
        super.onDestroy()
        // cancel scope
        cancel()
    }
}