package me.ruslanys.telegraff.core.event

import me.ruslanys.telegraff.core.dto.TelegramUpdate
import org.springframework.context.ApplicationEvent

class TelegramUpdateEvent(
        source: Any,
        val update: TelegramUpdate
) : ApplicationEvent(source)