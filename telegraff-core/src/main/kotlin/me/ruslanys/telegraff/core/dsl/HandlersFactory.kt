package me.ruslanys.telegraff.core.dsl

interface HandlersFactory {

    fun getHandlers(): Map<String, Handler>

}