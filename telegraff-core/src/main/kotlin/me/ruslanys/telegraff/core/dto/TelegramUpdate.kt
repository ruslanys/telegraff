package me.ruslanys.telegraff.core.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class TelegramUpdate(

        @JsonProperty("update_id")
        val id: Long,

        val message: TelegramMessage?,
        val editedMessage: TelegramMessage?,

        val channelPost: TelegramMessage?,
        val editedChannelPost: TelegramMessage?

)