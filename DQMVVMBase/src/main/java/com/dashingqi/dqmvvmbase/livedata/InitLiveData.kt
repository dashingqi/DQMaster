package com.dashingqi.dqmvvmbase.livedata

import androidx.lifecycle.MutableLiveData

/**
 * @author : zhangqi
 * @time : 12/6/20
 * desc : 必须带有默认值的LiveData
 */
class InitLiveData<T>(initValue: T) : MutableLiveData<T>(initValue) {
    override fun getValue(): T {
        return super.getValue()!!
    }

    override fun setValue(value: T) {
        super.setValue(value)
    }
}