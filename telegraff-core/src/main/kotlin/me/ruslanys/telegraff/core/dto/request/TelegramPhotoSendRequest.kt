package me.ruslanys.telegraff.core.dto.request

import com.fasterxml.jackson.annotation.JsonProperty

open class TelegramPhotoSendRequest(

        chatId: Long,

        @get:JsonProperty("photo")
        val photo: ByteArray,

        caption: String? = null,

        parseMode: TelegramParseMode? = null

) : TelegramMediaSendRequest(chatId, caption, parseMode)