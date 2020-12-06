package com.dashingqi.dqcommonutils

import java.math.BigDecimal

/**
 * @author : zhangqi
 * @time : 12/7/20
 * desc : 一般用于价格的计算
 */
object BigDecimalUtil {

    /**
     * 加法
     */
    fun add(first: BigDecimal, second: BigDecimal?): BigDecimal? {
        return first.add(second)
    }

    fun add(first: String?, second: String?): BigDecimal? {
        return add(BigDecimal(first), BigDecimal(second))
    }

    fun add(first: Double, second: Double): BigDecimal? {
        return add(first.toString(), second.toString())
    }

    fun add(first: String?, second: Double): BigDecimal? {
        return add(first, second.toString())
    }

    /**
     *  减法
     */
    fun subtract(first: BigDecimal, second: BigDecimal?): BigDecimal? {
        return first.subtract(second)
    }

    fun subtract(first: String?, second: String?): BigDecimal? {
        return subtract(BigDecimal(first), BigDecimal(second))
    }

    fun subtract(first: String?, second: Double): BigDecimal? {
        return subtract(first, second.toString())
    }

    /**
     * 乘法
     */
    fun multiply(first: BigDecimal, second: BigDecimal?): BigDecimal? {
        return first.multiply(second)
    }

    fun multiply(first: String?, second: String?): BigDecimal? {
        return multiply(BigDecimal(first), BigDecimal(second))
    }

    fun multiply(first: String?, second: Double): BigDecimal? {
        return multiply(first, second.toString())
    }

}