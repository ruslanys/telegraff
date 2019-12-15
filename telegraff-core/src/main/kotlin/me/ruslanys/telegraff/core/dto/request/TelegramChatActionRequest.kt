package me.ruslanys.telegraff.core.dto.request

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

@JsonInclude(JsonInclude.Include.NON_NULL)
open class TelegramChatActionRequest(

        @get:JsonProperty("chat_id")
        var chatId: Long,

        @get:JsonProperty("action")
        val action: TelegramChatAction

) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as TelegramChatActionRequest

        if (chatId != other.chatId) return false
        if (action != other.action) return false

        return true
    }

    override fun hashCode(): Int {
        var result = chatId.hashCode()
        result = 31 * result + action.hashCode()
        return result
    }

}
