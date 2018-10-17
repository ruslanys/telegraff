package me.ruslanys.telegraff.core.annotation

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS)
annotation class TelegramFilterOrder(val value: Int)