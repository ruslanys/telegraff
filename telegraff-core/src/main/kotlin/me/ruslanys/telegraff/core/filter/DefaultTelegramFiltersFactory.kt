package me.ruslanys.telegraff.core.filter

import me.ruslanys.telegraff.core.util.TelegramFilterOrderUtil

class DefaultTelegramFiltersFactory(filters: List<TelegramFilter>) : TelegramFiltersFactory {

    private val filters: List<TelegramFilter> = filters.sortedBy {
        TelegramFilterOrderUtil.getOrder(it::class.java)
    }

    override fun getFilters(): List<TelegramFilter> {
        return filters
    }

}