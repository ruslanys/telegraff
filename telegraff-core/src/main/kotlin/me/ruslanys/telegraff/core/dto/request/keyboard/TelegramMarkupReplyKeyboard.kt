package me.ruslanys.telegraff.core.dto.request.keyboard

import com.fasterxml.jackson.annotation.JsonProperty

class TelegramMarkupReplyKeyboard(
        answers: List<String>,
        columns: Int = DEFAULT_COLUMNS_NUMBER,

        @get:JsonProperty("resize_keyboard")
        val resizeKeyboard: Boolean = true,

        @get:JsonProperty("one_time_keyboard")
        val oneTimeKeyboard: Boolean = true
) : TelegramReplyKeyboard() {

    val keyboard: List<List<String>> = answers.asSequence()
            .chunked(columns)
            .plusElement(listOf("Отмена"))
            .toList()

    companion object {
        private const val DEFAULT_COLUMNS_NUMBER = 2
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is TelegramMarkupReplyKeyboard) return false
        if (!super.equals(other)) return false

        if (resizeKeyboard != other.resizeKeyboard) return false
        if (oneTimeKeyboard != other.oneTimeKeyboard) return false
        if (keyboard != other.keyboard) return false

        return true
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + resizeKeyboard.hashCode()
        result = 31 * result + oneTimeKeyboard.hashCode()
        result = 31 * result + keyboard.hashCode()
        return result
    }


}