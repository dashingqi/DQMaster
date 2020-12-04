package com.dashingqi.dqcountdown.utils

import com.dashingqi.dqcountdown.bean.TimeBean

/**
 * @author : zhangqi
 * @time : 12/4/20
 * desc :
 */
object TimeUtils {

    fun formatTimeLong(millisecond: Long,timeBean: TimeBean): TimeBean {
        var hour = (millisecond / 1000 / (60 * 60)).toInt()
        var minute = (millisecond / 1000 / 60 % 60).toInt()
        var second = (millisecond / 1000 % 60).toInt()
        return if (hour < 10) {
            if (minute < 10) {
                if (second < 10) {
                    handleTime("0$hour", "0$minute", "0$second", timeBean)
                } else {
                    handleTime("0$hour", "0$minute", "$second", timeBean)
                }
            } else {
                if (second < 10) {
                    handleTime("0$hour", "$minute", "0$second", timeBean)
                } else {
                    handleTime("0$hour", "$minute", "$second", timeBean)
                }
            }
        } else {
            if (minute < 10) {
                if (second < 10) {
                    handleTime("$hour", "0$minute", "0$second", timeBean)
                } else {

                    handleTime("0$hour", "0$minute", "$second", timeBean)
                }
            } else {
                if (second < 10) {
                    handleTime("$hour", "$minute", "0$second", timeBean)
                } else {
                    handleTime("$hour", "$minute", "$second", timeBean)
                }
            }
        }
    }

    private fun handleTime(hour: String, minute: String, second: String, timeBean: TimeBean): TimeBean {
        timeBean.hour = hour
        timeBean.minute = minute
        timeBean.second = second
        return timeBean
    }
}