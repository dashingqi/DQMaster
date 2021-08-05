package com.dashingqi.master

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * @author : zhangqi
 * @time : 12/4/20
 * desc :
 */
class MainViewModel:ViewModel() {

    var countDownTime = MutableLiveData("10000")

    init {

    }
}