package me.ruslanys.telegraff.core.filter

import me.ruslanys.telegraff.core.event.TelegramUpdateEvent
import org.springframework.context.event.EventListener

interface TelegramFilterProcessor {

    @EventListener(TelegramUpdateEvent::class)
    fun process(event: TelegramUpdateEvent)

}