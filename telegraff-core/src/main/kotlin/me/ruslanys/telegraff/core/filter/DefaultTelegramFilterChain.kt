package me.ruslanys.telegraff.core.filter

import me.ruslanys.telegraff.core.dto.TelegramMessage


class DefaultTelegramFilterChain(filters: List<TelegramFilter>) : TelegramFilterChain {

    private val iterator: Iterator<TelegramFilter> = filters.iterator()

    override fun doFilter(message: TelegramMessage) {
        if (iterator.hasNext()) {
            val filter = iterator.next()
            filter.handleMessage(message, this)
        }
    }

}