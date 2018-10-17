package me.ruslanys.telegraff.core.dto.request

class TelegramPhotoSendRequest(
        chatId: Long,
        val photo: ByteArray,
        caption: String? = null,
        parseMode: TelegramParseMode? = null
) : TelegramMediaSendRequest(chatId, caption, parseMode)