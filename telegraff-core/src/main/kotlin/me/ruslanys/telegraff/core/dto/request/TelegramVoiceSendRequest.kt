package me.ruslanys.telegraff.core.dto.request

class TelegramVoiceSendRequest(
        chatId: Long,
        val voice: ByteArray,
        caption: String? = null,
        parseMode: TelegramParseMode? = null
) : TelegramMediaSendRequest(chatId, caption, parseMode)