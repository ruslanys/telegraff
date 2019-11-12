package me.ruslanys.telegraff.core.client

import me.ruslanys.telegraff.core.component.TelegramApi
import me.ruslanys.telegraff.core.dto.TelegramUpdate
import me.ruslanys.telegraff.core.event.TelegramUpdateEvent
import org.slf4j.LoggerFactory
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.ApplicationEventPublisher
import org.springframework.context.ApplicationListener
import java.util.concurrent.TimeUnit
import javax.annotation.PreDestroy

class TelegramPollingClient(
        private val telegramApi: TelegramApi,
        private val publisher: ApplicationEventPublisher
) : TelegramClient, ApplicationListener<ApplicationReadyEvent> {

    private val client = Client()

    override fun onApplicationEvent(event: ApplicationReadyEvent) {
        start()
    }

    override fun start() {
        log.info("Telegram client: POLLING")
        client.start()
    }

    @PreDestroy
    override fun shutdown() {
        client.interrupt()
        client.join()
    }

    override fun onUpdate(update: TelegramUpdate) {
        log.info("Got a new event: {}", update)
        publisher.publishEvent(TelegramUpdateEvent(this, update))
    }

    private inner class Client : Thread("PollingClient") {

        private var offset: Long = 0

        override fun run() {
            while (!isInterrupted) {
                try {
                    val updates = telegramApi.getUpdates(offset, POLLING_TIMEOUT)
                    if (updates.isEmpty()) {
                        continue
                    }

                    // publish updates
                    for (update in updates) {
                        onUpdate(update)
                    }

                    // --
                    offset = updates.last().id + 1
                } catch (e: Exception) {
                    log.error("Exception during polling messages", e)
                    TimeUnit.SECONDS.sleep(10)
                }
            }
        }

    }

    companion object {
        private val log = LoggerFactory.getLogger(TelegramPollingClient::class.java)
        private const val POLLING_TIMEOUT = 10
    }


}