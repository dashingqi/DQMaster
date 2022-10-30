package com.dashingqi.dqcommonutils.ext

import org.json.JSONArray
import org.json.JSONObject

/**
 * 在optString的基础上判断了返回值是否为null的情况
 * {"key1":null}的optString("key1","fallback")会返回"null"字符串，而不是"fallback"
 * 同时忽略了"null"字符串（忽略了大小写检查）
 * @receiver JSONObject? 可空的JSONObject对象
 * @param name String? key
 * @param fallback String 默认值
 * @return String 值
 */
fun JSONObject?.optStringIgnoreNull(name: String?, fallback: String): String {
    if (this == null) return fallback
    return when (val value = this.opt(name)) {
        null -> fallback
        JSONObject.NULL -> fallback
        is String -> {
            if ("null".equals(value, true)) fallback else value
        }
        else -> value.toString()
    }
}

/** 当JSONArray中存储对象【JSONObject】时遍历*/
inline fun JSONArray.forEachObject(action: (JSONObject?) -> Unit) {
    for (index in 0..this.length()) action(optJSONObject(index))
}

/** 当JSONArray中存储对象【JSONObject】时遍历.并按照transform的规则返回非空对象集合*/
inline fun <R> JSONArray.mapEveryNotNull(transform: (JSONObject?) -> R?): List<R> {
    val everyList = mutableListOf<R>()
    forEachObject { item -> transform(item)?.let { everyList.add(it) } }
    return everyList
}

/** 当JSONArray中存储元素为【Int】时遍历*/
inline fun JSONArray.forEachInt(fallback: Int, action: (Int) -> Unit) {
    for (index in 0..this.length()) action(optInt(index, fallback))
}

/** JSONArray[Int]转换成非空的List集合*/
fun JSONArray.mapIntNotNull(fallback: Int): List<Int> {
    val ints = mutableListOf<Int>()
    forEachInt(fallback) { item -> ints.add(item) }
    return ints
}

/** 当JSONArray中存储元素为【String】时遍历*/
inline fun JSONArray.forEachString(fallback: String, action: (String) -> Unit) {
    for (index in 0..this.length()) action(optString(index, fallback))
}

/** JSONArray[String]转换成非空的List集合*/
fun JSONArray.mapStringNotNull(fallback: String): List<String> {
    val strings = mutableListOf<String>()
    forEachString(fallback) { item -> strings.add(item) }
    return strings
}

