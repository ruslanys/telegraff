package me.ruslanys.telegraff.core.filter

import me.ruslanys.telegraff.core.util.TelegramFilterOrderUtil
import org.springframework.context.ApplicationContext
import javax.annotation.PostConstruct

class DefaultTelegramFiltersFactory(private val context: ApplicationContext) : TelegramFiltersFactory {

    private lateinit var filters: List<TelegramFilter>

    @PostConstruct
    private fun init() {
        filters = context.getBeansOfType(TelegramFilter::class.java)
                .values
                .sortedBy { TelegramFilterOrderUtil.getOrder(it.javaClass) }
    }

    override fun getFilters(): List<TelegramFilter> {
        return filters
    }

}