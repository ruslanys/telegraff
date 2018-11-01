package me.ruslanys.telegraff.core.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class TelegramChat(

        @JsonProperty("id")
        val id: Long,

        /**
         * PRIVATE, GROUP, SUPERGROUP, CHANNEL
         */
        @JsonProperty("type")
        val type: String,

        @JsonProperty("title")
        val title: String?,

        @JsonProperty("username")
        val username: String?,

        @JsonProperty("first_name")
        val firstName: String?,

        @JsonProperty("last_name")
        val lastName: String?

)