package com.dashingqi.dqimageselector.enum

import java.util.*

/**
 * @desc : 媒体文件类别枚举
 * @author : zhangqi
 * @time : 2021/8/24 01:21
 */
enum class MimeTypeEnum(var mimeType: String, var extSet: Set<String>) {

    PNG("image/png", setOf<String>("png")),

    WEBP("image/webp", setOf("webp")),

    JPEG("image/jpeg", setOf("jpeg", "jpg")),

    GIF("image/gif", setOf("gif")),

    BMP("image/x-ms-bmp", setOf("bmp"));


    companion object {

        /**
         * 全部类型
         * @return Set<MimeTypeEnum> 所有类型集合
         */
        fun ofAll(): Set<MimeTypeEnum> {
            return EnumSet.allOf(MimeTypeEnum::class.java)
        }

        /**
         * 图片类型
         * @return Set<MimeTypeEnum> 图片类型集合
         */
        fun ofImage(): Set<MimeTypeEnum> {
            return EnumSet.of(PNG, WEBP, JPEG, GIF, BMP)
        }

        /**
         * gif 类型
         * @param onlyGif Boolean
         * @return Set<MimeTypeEnum>
         */
        fun ofImage(onlyGif: Boolean): Set<MimeTypeEnum> {
            return EnumSet.of(GIF)
        }

        /**
         * gif类型 内部调用了ofImage
         * @return Set<MimeTypeEnum>
         */
        fun ofGif(): Set<MimeTypeEnum> {
            return ofImage(true)
        }

    }
}