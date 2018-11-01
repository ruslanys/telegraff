package me.ruslanys.telegraff.autoconfigure

import me.ruslanys.telegraff.autoconfigure.property.TelegramProperties
import me.ruslanys.telegraff.autoconfigure.property.TelegramPropertiesValidator
import me.ruslanys.telegraff.core.client.TelegramPollingClient
import me.ruslanys.telegraff.core.client.TelegramWebhookClient
import me.ruslanys.telegraff.core.component.TelegramApi
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.validation.Validator

/**
 * Enable Auto-Configuration for Telegraff.
 *
 * @author Ruslan Molchanov
 */
@Configuration
@ConditionalOnClass(TelegramPollingClient::class, TelegramWebhookClient::class, TelegramApi::class)
@EnableConfigurationProperties(TelegramProperties::class)
@Import(TelegraffNonWebConfiguration::class, TelegraffServletWebConfiguration::class)
class TelegraffAutoConfiguration {

    companion object {
        @Bean
        fun configurationPropertiesValidator(): Validator {
            return TelegramPropertiesValidator()
        }
    }

}