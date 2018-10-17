package me.ruslanys.telegraff.core.util

import me.ruslanys.telegraff.core.annotation.TelegramFilterOrder
import org.springframework.core.annotation.AnnotationUtils

object TelegramFilterOrderUtil {

    fun getOrder(type: Class<*>): Int {
        return AnnotationUtils.findAnnotation(type, TelegramFilterOrder::class.java)?.value ?: 0
    }

}