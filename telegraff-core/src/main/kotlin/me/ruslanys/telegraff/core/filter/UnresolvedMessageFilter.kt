package me.ruslanys.telegraff.core.filter

import me.ruslanys.telegraff.core.annotation.TelegramFilterOrder
import me.ruslanys.telegraff.core.component.TelegramApi
import me.ruslanys.telegraff.core.dto.TelegramMessage
import me.ruslanys.telegraff.core.dto.request.TelegramMessageSendRequest
import me.ruslanys.telegraff.core.dto.request.TelegramParseMode

@TelegramFilterOrder(Integer.MAX_VALUE)
class UnresolvedMessageFilter(private val telegramApi: TelegramApi): TelegramFilter {

    override fun handleMessage(message: TelegramMessage, chain: TelegramFilterChain) {
        if ("PRIVATE".equals(message.chat.type, true)) {
            val request = TelegramMessageSendRequest(
                    message.chat.id,
                    "Извини, я тебя не понимаю",
                    TelegramParseMode.MARKDOWN
            )
            telegramApi.sendMessage(request)
        }
    }

}