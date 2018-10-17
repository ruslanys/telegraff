package me.ruslanys.telegraff.core.filter

import me.ruslanys.telegraff.core.annotation.TelegramFilterOrder
import me.ruslanys.telegraff.core.component.TelegramApi
import me.ruslanys.telegraff.core.dto.TelegramMessage
import me.ruslanys.telegraff.core.dto.request.MarkdownMessage

@TelegramFilterOrder(0)
class CancelFilter(private val telegramApi: TelegramApi, private val handlersFilter: HandlersFilter): TelegramFilter {

    override fun handleMessage(message: TelegramMessage, chain: TelegramFilterChain) {
        val text = message.text?.toLowerCase() ?: ""
        if (text.startsWith("/cancel") || text.startsWith("отмена")) {
            handlersFilter.clearState(message.chat)
            telegramApi.sendMessage(MarkdownMessage("Хорошо, давай начнем сначала", chatId = message.chat.id))
        } else {
            chain.doFilter(message)
        }
    }

}