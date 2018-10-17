package me.ruslanys.telegraff.core.client

import me.ruslanys.telegraff.core.component.TelegramApi
import me.ruslanys.telegraff.core.dto.TelegramUpdate
import me.ruslanys.telegraff.core.event.TelegramUpdateEvent
import org.slf4j.LoggerFactory
import org.springframework.context.ApplicationEventPublisher
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.annotation.PostConstruct
import javax.annotation.PreDestroy

@RestController
class TelegramWebhookClient(
        private val telegramApi: TelegramApi,
        private val publisher: ApplicationEventPublisher,
        private val webhookUrl: String
) : TelegramClient {

    @PostConstruct
    override fun start() {
        log.info("Telegram client: WEBHOOK")
        log.info("Setting webhook at: {}", webhookUrl)
        telegramApi.setWebhook(webhookUrl)
    }

    @PreDestroy
    override fun shutdown() {
        log.info("Removing webhook")
        telegramApi.removeWebhook()
    }

    override fun onUpdate(update: TelegramUpdate) {
        log.info("Got a new event: {}", update)
        publisher.publishEvent(TelegramUpdateEvent(this, update))
    }

    @RequestMapping("#{telegramProperties.getWebhookEndpointUrl()}")
    fun update(@RequestBody update: TelegramUpdate): ResponseEntity<String> {
        onUpdate(update)
        return ResponseEntity.ok("ok")
    }

    companion object {
        private val log = LoggerFactory.getLogger(TelegramWebhookClient::class.java)
    }

}