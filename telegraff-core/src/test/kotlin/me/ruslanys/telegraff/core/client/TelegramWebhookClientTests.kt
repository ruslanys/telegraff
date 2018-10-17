package me.ruslanys.telegraff.core.client

import me.ruslanys.telegraff.core.component.TelegramApi
import org.assertj.core.api.Assertions.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.boot.test.rule.OutputCapture
import org.springframework.context.ApplicationEventPublisher
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status


@RunWith(SpringRunner::class)
@WebMvcTest(TelegramWebhookClient::class)
class TelegramWebhookClientTests {

    @get:Rule val capture = OutputCapture()

    @Autowired private lateinit var mvc: MockMvc

    @MockBean private lateinit var telegramApi: TelegramApi


    @Test
    fun `Update with valid token`() {
        mvc.perform(post("/telegram")
                .contentType(MediaType.APPLICATION_JSON)
                .content(getUpdateJson()))

                .andExpect(status().isOk)
                .andExpect(content().string("ok"))


        // https://github.com/spring-projects/spring-boot/issues/6060
        // ¯\_(ツ)_/¯
        assertThat(capture.toString()).contains("366468163")
        assertThat(capture.toString()).contains("ruslanys")
        assertThat(capture.toString()).contains("Как дела?")
    }

    private fun getUpdateJson(): String = """
        {
          "update_id": 366468163,
          "message": {
            "message_id": 5363,
            "from": {
              "id": 73168307,
              "is_bot": false,
              "first_name": "Ruslan",
              "last_name": "Molchanov",
              "username": "ruslanys",
              "language_code": "en-BY"
            },
            "chat": {
              "id": 73168307,
              "first_name": "Ruslan",
              "last_name": "Molchanov",
              "username": "ruslanys",
              "type": "private"
            },
            "date": 1538990403,
            "text": "Как дела?"
          }
        }
        """.trimIndent()


    @Configuration
    class Config {

        @Bean(name = ["telegramProperties"])
        fun telegramProperties() = Properties()

        @Bean
        fun telegramWebhookClient(telegramApi: TelegramApi, publisher: ApplicationEventPublisher): TelegramWebhookClient {
            return TelegramWebhookClient(telegramApi, publisher, "http://localhost/telegram")
        }

        class Properties {

            fun getWebhookEndpointUrl() = "/telegram"

        }

    }

}