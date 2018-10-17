package me.ruslanys.telegraff.core.dto.request.keyboard

abstract class TelegramReplyKeyboard(val selective: Boolean = false) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is TelegramReplyKeyboard) return false

        if (selective != other.selective) return false

        return true
    }

    override fun hashCode(): Int {
        return selective.hashCode()
    }

}