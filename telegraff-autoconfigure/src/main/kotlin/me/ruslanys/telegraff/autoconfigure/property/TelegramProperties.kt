package me.ruslanys.telegraff.autoconfigure.property

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component
import java.util.*

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
     * Webhook properties.
     */
    var webhook: WebhookProperties = WebhookProperties()

    /**
     * Path where handlers declaration stored.
     */
    var handlersPath = "handlers"

    /**
     * UnresolvedMessageFilter properties.
     */
    var unresolvedFilter = UnresolvedMessageFilterProperties()


    fun getWebhookUrl(): String = "${webhook.baseUrl}${webhook.endpointUrl}"


    class WebhookProperties {
        /**
         * Webhook base URL.
         * For example, https://localhost:8443.
         */
        var baseUrl: String? = null

        /**
         * Webhook endpoint url.
         * For example, /telegram.
         */
        var endpointUrl: String = "/telegram/" + UUID.randomUUID().toString()
    }

    class UnresolvedMessageFilterProperties {
        /**
         * Enable UnresolvedMessageFilter.
         */
        var enabled: Boolean = true
    }

}
