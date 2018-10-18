package me.ruslanys.telegraff.core.filter

import me.ruslanys.telegraff.core.event.TelegramUpdateEvent
import me.ruslanys.telegraff.core.util.TelegramFilterOrderUtil

class DefaultTelegramFiltersFactory(filters: List<TelegramFilter>) : TelegramFiltersFactory, TelegramFilterProcessor {

    private val filters: List<TelegramFilter> = filters.sortedBy {
        TelegramFilterOrderUtil.getOrder(it::class.java)
    }

    override fun getFilters(): List<TelegramFilter> {
        return filters
    }

    override fun process(event: TelegramUpdateEvent) {
        if (event.update.message != null) { // only new messages are supported
            val chain = DefaultTelegramFilterChain(filters)
            chain.doFilter(event.update.message)
        }
    }

}