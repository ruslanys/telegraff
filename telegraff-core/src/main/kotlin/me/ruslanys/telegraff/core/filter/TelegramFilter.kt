package me.ruslanys.telegraff.core.filter

import me.ruslanys.telegraff.core.dto.TelegramMessage

interface TelegramFilter {

    fun handleMessage(message: TelegramMessage, chain: TelegramFilterChain)

}