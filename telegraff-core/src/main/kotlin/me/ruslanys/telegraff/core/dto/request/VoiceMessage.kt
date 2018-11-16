package me.ruslanys.telegraff.core.dto.request

class VoiceMessage(
        photo: ByteArray,
        caption: String? = null,
        parseMode: TelegramParseMode? = null,
        chatId: Long = 0
) : TelegramVoiceSendRequest(chatId, photo, caption, parseMode)