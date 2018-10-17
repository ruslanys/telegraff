package me.ruslanys.telegraff.core.dto.request

import me.ruslanys.telegraff.core.dto.request.keyboard.TelegramMarkupReplyKeyboard
import me.ruslanys.telegraff.core.dto.request.keyboard.TelegramRemoveReplyKeyboard

class HtmlMessage(text: String, vararg replies: String, chatId: Long = 0) : TelegramMessageSendRequest(
        chatId,
        text,
        TelegramParseMode.HTML,
        if (replies.isNotEmpty()) TelegramMarkupReplyKeyboard(replies.asList()) else TelegramRemoveReplyKeyboard()
)