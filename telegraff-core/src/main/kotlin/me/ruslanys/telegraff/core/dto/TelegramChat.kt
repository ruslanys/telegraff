package me.ruslanys.telegraff.core.dto

data class TelegramChat(
        val id: Long,
        val type: String, // PRIVATE, GROUP, SUPERGROUP, CHANNEL
        val title: String?,
        val username: String?,
        val firstName: String?,
        val lastName: String?
)