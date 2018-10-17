package me.ruslanys.telegraff.autoconfigure.property

import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException
import org.springframework.boot.context.properties.ConfigurationPropertiesBindException
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.validation.Validator

class TelegramPropertiesTests {

    @get:Rule
    val thrown = ExpectedException.none()!!

    private val context = AnnotationConfigApplicationContext()

    @After
    fun closeContext() {
        this.context.close()
    }

    @Test
    fun bindValidPropertiesTest() {
        this.context.register(Config::class.java)
        TestPropertyValues.of(
                "telegram.accessKey:key", "telegram.mode:webhook", "telegram.webhookBaseUrl:https://localhost")
                .applyTo(this.context)

        this.context.refresh()

        val properties = this.context.getBean(TelegramProperties::class.java)

        assertThat(properties.accessKey).isEqualTo("key")
        assertThat(properties.mode).isEqualTo(TelegramMode.WEBHOOK)
        assertThat(properties.webhookBaseUrl).isEqualTo("https://localhost")
    }

    @Test
    fun bindEmptyKey() {
        this.context.register(Config::class.java)
        TestPropertyValues.of(
                "telegram.accessKey:", "telegram.mode:webhook", "telegram.webhookBaseUrl:https://localhost")
                .applyTo(this.context)
        this.thrown.expect(ConfigurationPropertiesBindException::class.java)

        this.context.refresh()
    }

    @Test
    fun bindInvalidMode() {
        this.context.register(Config::class.java)
        TestPropertyValues.of(
                "telegram.accessKey:key", "telegram.mode:ASD", "telegram.webhookBaseUrl:https://localhost")
                .applyTo(this.context)
        this.thrown.expect(ConfigurationPropertiesBindException::class.java)

        this.context.refresh()
    }

    @Test
    fun bindWebhookWithoutBaseUrl() {
        this.context.register(Config::class.java)
        TestPropertyValues.of(
                "telegram.accessKey:key", "telegram.mode:webhook")
                .applyTo(this.context)
        this.thrown.expect(ConfigurationPropertiesBindException::class.java)

        this.context.refresh()
    }

    @Test
    fun bindWebhookBaseUrlWithHttp() {
        this.context.register(Config::class.java)
        TestPropertyValues.of(
                "telegram.accessKey:key", "telegram.mode:webhook", "telegram.webhookBaseUrl:http://localhost")
                .applyTo(this.context)

        this.thrown.expect(ConfigurationPropertiesBindException::class.java)

        this.context.refresh()
    }



    @Configuration
    @EnableConfigurationProperties(TelegramProperties::class)
    class Config {
        companion object {
            @JvmStatic
            @Bean
            fun configurationPropertiesValidator(): Validator {
                return TelegramPropertiesValidator()
            }
        }
    }

}