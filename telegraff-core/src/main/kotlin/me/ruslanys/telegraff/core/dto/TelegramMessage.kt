package me.ruslanys.telegraff.core.dto

import com.fasterxml.jackson.annotation.JsonProperty
import java.util.*

data class TelegramMessage(

        @JsonProperty("message_id")
        val id: Long,

        @JsonProperty("from")
        val user: TelegramUser?,

        val date: Long,

        val chat: TelegramChat,

        val text: String?

) {

    fun getDate(): Date = Date(date * 1000)

}