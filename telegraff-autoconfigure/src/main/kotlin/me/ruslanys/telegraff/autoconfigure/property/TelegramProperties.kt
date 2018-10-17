package me.ruslanys.telegraff.autoconfigure.property

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component
import java.util.*
import javax.validation.constraints.NotEmpty

@Component("telegramProperties")
@ConfigurationProperties(prefix = "telegram", ignoreUnknownFields = false)
class TelegramProperties {

    /**
     * Telegram Bot API Access Key.
     */
    var accessKey: String = ""

    /**
     * Telegram updates mode.
     */
    var mode = TelegramMode.POLLING

    /**
     * Webhook base URL.
     * For example, https://localhost:8443.
     */
    var webhookBaseUrl: String? = null

    /**
     * Webhook endpoint url.
     * For example, /telegram.
     */
    var webhookEndpointUrl: String = "/telegram/" + UUID.randomUUID().toString()

    /**
     * Path where handlers declaration stored.
     */
    @NotEmpty
    var scenariosPath = "scenarios"


    fun getWebhookUrl(): String = "$webhookBaseUrl$webhookEndpointUrl"

}
