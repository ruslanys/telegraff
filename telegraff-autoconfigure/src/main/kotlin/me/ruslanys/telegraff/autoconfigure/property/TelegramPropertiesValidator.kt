package me.ruslanys.telegraff.autoconfigure.property

import org.springframework.validation.Errors
import org.springframework.validation.Validator

class TelegramPropertiesValidator : Validator {

    override fun supports(type: Class<*>): Boolean {
        return type === TelegramProperties::class.java
    }

    override fun validate(target: Any, errors: Errors) {
        val properties = target as TelegramProperties

        if (properties.accessKey.isEmpty()) {
            errors.rejectValue("accessKey", "accessKey.null",
                    "Telegram Access Key must not be null!")
        }

        if (properties.handlersPath.isEmpty()) {
            errors.rejectValue("handlersPath", "handlersPath.null",
                    "Telegram handlers path must not be null!")
        }

        if (properties.mode == TelegramMode.WEBHOOK) {
            val webhookBaseUrl = properties.webhookBaseUrl
            if (webhookBaseUrl == null || webhookBaseUrl.isEmpty()) {
                errors.rejectValue("webhookBaseUrl", "webhookBaseUrl.empty",
                        "You have to set Webhook Base URL with Webhook mode.")
            } else if (!webhookBaseUrl.startsWith("https://")) {
                errors.rejectValue("webhookBaseUrl", "webhookBaseUrl.https",
                        "You have to set HTTPS protocol at Webhook base URL.")
            }

        }
    }

}