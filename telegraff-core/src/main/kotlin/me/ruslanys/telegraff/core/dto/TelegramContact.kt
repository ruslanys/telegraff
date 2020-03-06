package me.ruslanys.telegraff.core.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class TelegramContact(

        @JsonProperty("user_id")
        val userId: Long,

        @JsonProperty("first_name")
        val firstName: String,

        @JsonProperty("last_name")
        val lastName: String?,

        @JsonProperty("phone_number")
        val phoneNumber: String?

)