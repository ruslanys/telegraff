package me.ruslanys.telegraff.core.dto.request.keyboard

import com.fasterxml.jackson.annotation.JsonProperty

class TelegramRemoveReplyKeyboard : TelegramReplyKeyboard() {

    @JsonProperty("remove_keyboard")
    fun getRemoveKeyboard(): Boolean {
        return true
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is TelegramRemoveReplyKeyboard) return false
        if (!super.equals(other)) return false
        return true
    }

    @Suppress("RedundantOverride")
    override fun hashCode(): Int {
        return super.hashCode()
    }


}