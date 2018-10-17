package me.ruslanys.telegraff.core.dsl

import me.ruslanys.telegraff.core.dto.TelegramChat
import java.util.concurrent.ConcurrentHashMap

class HandlerState(
        val chat: TelegramChat,
        val handler: Handler
) {

    var currentStep: Step<*>? = handler.getInitialStep()
    val answers: MutableMap<String, Any> = ConcurrentHashMap()
    val attributes: MutableMap<String, Any> = ConcurrentHashMap()

}