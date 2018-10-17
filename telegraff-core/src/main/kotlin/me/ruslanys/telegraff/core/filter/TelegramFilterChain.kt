package me.ruslanys.telegraff.core.filter

import me.ruslanys.telegraff.core.dto.TelegramMessage

interface TelegramFilterChain {

    fun doFilter(message: TelegramMessage)

}