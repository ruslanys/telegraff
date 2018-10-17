package me.ruslanys.telegraff.core.exception

open class HandlerException(override val message: String) : RuntimeException(message)