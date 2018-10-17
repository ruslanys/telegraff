package me.ruslanys.telegraff.core.filter

interface TelegramFiltersFactory {

    fun getFilters(): List<TelegramFilter>

}