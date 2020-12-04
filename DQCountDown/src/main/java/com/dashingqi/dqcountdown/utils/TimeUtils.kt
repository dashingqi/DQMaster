package com.dashingqi.dqcountdown.utils

import com.dashingqi.dqcountdown.bean.TimeBean

/**
 * @author : zhangqi
 * @time : 12/4/20
 * desc :
 */
object TimeUtils {
    fun formatTimeLong(millisecond: Long):TimeBean{
        var hour = (millisecond / 1000 / (60 * 60)).toInt()
        var minute = (millisecond / 1000 / 60 % 60).toInt()
        var second = (millisecond / 1000 % 60).toInt()
        return if (hour < 10) {
            if (minute < 10) {
                if (second < 10) {
                    TimeBean("0$hour", "0$minute", "0$second")
                } else {
                    TimeBean("0$hour", "0$minute", "$second")
                }
            } else {
                if (second < 10) {
                    TimeBean("0$hour", "$minute", "0$second")
                } else {
                    TimeBean("0$hour", "$minute", "$second")
                }
            }
        } else {
            if (minute < 10) {
                if (second < 10) {
                    TimeBean("$hour", "0$minute", "0$second")
                } else {

                    TimeBean("0$hour", "0$minute", "$second")
                }
            } else {
                if (second < 10) {
                    TimeBean("$hour", "$minute", "0$second")
                } else {
                    TimeBean("$hour", "$minute", "$second")
                }
            }
        }
    }
}