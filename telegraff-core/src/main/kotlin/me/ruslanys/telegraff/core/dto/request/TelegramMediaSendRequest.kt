package me.ruslanys.telegraff.core.dto.request

import com.fasterxml.jackson.annotation.JsonProperty
import me.ruslanys.telegraff.core.dto.request.keyboard.TelegramRemoveReplyKeyboard

abstract class TelegramMediaSendRequest(
        chatId: Long,

        @get:JsonProperty("caption")
        val caption: String? = null,

        @get:JsonProperty("parse_mode")
        val parseMode: TelegramParseMode? = null

) : TelegramSendRequest(chatId, TelegramRemoveReplyKeyboard())