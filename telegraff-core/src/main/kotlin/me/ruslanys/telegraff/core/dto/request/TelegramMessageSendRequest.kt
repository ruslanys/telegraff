package me.ruslanys.telegraff.core.dto.request

import com.fasterxml.jackson.annotation.JsonProperty
import me.ruslanys.telegraff.core.dto.request.keyboard.TelegramRemoveReplyKeyboard
import me.ruslanys.telegraff.core.dto.request.keyboard.TelegramReplyKeyboard

open class TelegramMessageSendRequest(

        chatId: Long,

        @get:JsonProperty("text")
        val text: String,

        @get:JsonProperty("parse_mode")
        val parseMode: TelegramParseMode,

        replyMarkup: TelegramReplyKeyboard = TelegramRemoveReplyKeyboard(),

        disableNotification: Boolean = false,

        @get:JsonProperty("disable_web_page_preview")
        val disableWebPagePreview: Boolean = false

) : TelegramSendRequest(chatId, replyMarkup, disableNotification) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is TelegramMessageSendRequest) return false
        if (!super.equals(other)) return false

        if (text != other.text) return false
        if (parseMode != other.parseMode) return false
        if (disableWebPagePreview != other.disableWebPagePreview) return false

        return true
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + text.hashCode()
        result = 31 * result + parseMode.hashCode()
        result = 31 * result + disableWebPagePreview.hashCode()
        return result
    }

}