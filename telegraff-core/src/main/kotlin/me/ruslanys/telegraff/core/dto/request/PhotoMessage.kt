package me.ruslanys.telegraff.core.dto.request

class PhotoMessage(
        photo: ByteArray,
        caption: String? = null,
        parseMode: TelegramParseMode? = null,
        chatId: Long = 0
) : TelegramPhotoSendRequest(chatId, photo, caption, parseMode)