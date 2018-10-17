package me.ruslanys.telegraff.core.dto.request

import me.ruslanys.telegraff.core.dto.request.keyboard.TelegramRemoveReplyKeyboard

abstract class TelegramMediaSendRequest(
        chatId: Long,
        val caption: String? = null,
        val parseMode: TelegramParseMode? = null
) : TelegramSendRequest(chatId, TelegramRemoveReplyKeyboard())