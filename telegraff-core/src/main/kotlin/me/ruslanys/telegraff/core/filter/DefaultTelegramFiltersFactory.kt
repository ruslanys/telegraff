package me.ruslanys.telegraff.core.filter

import org.springframework.context.ApplicationContext
import org.springframework.core.annotation.OrderUtils

class DefaultTelegramFiltersFactory(context: ApplicationContext) : TelegramFiltersFactory {

    private val filters: List<TelegramFilter> = context.getBeansOfType(TelegramFilter::class.java)
            .values
            .sortedBy { OrderUtils.getOrder(it.javaClass, 0) }


    override fun getFilters(): List<TelegramFilter> {
        return filters
    }

}