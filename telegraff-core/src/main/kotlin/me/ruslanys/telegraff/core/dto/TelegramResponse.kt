package me.ruslanys.telegraff.core.dto

data class TelegramResponse<T>(
        val ok: Boolean,
        val result: T?,

        // in case of error
        val errorCode: Int?,
        val description: String?
)
