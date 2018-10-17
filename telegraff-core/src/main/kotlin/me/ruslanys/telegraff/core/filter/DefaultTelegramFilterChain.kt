package me.ruslanys.telegraff.core.filter

import me.ruslanys.telegraff.core.dto.TelegramMessage
import me.ruslanys.telegraff.core.event.TelegramUpdateEvent
import org.springframework.context.ApplicationListener


class DefaultTelegramFilterChain(
        private val telegramFiltersFactory: TelegramFiltersFactory
) : TelegramFilterChain, ApplicationListener<TelegramUpdateEvent> {

    private val iterator: Iterator<TelegramFilter> by lazy {
        telegramFiltersFactory.getFilters().iterator()
    }

    override fun onApplicationEvent(event: TelegramUpdateEvent) {
        if (event.update.message != null) { // only new messages are supported
            doFilter(event.update.message)
        }
    }

    override fun doFilter(message: TelegramMessage) {
        if (iterator.hasNext()) {
            val filter = iterator.next()
            filter.handleMessage(message, this)
        }
    }

}