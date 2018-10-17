package me.ruslanys.telegraff.core.component

import me.ruslanys.telegraff.core.dto.request.TelegramMessageSendRequest
import me.ruslanys.telegraff.core.dto.request.TelegramParseMode
import me.ruslanys.telegraff.core.dto.request.TelegramPhotoSendRequest
import me.ruslanys.telegraff.core.dto.request.TelegramVoiceSendRequest
import me.ruslanys.telegraff.core.dto.request.keyboard.TelegramMarkupReplyKeyboard
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.client.MockRestServiceServer
import org.springframework.test.web.client.match.MockRestRequestMatchers.*
import org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess

@RunWith(SpringRunner::class)
@RestClientTest(DefaultTelegramApi::class)
class DefaultTelegramApiTests {

    @Autowired
    private lateinit var api: DefaultTelegramApi

    @Autowired
    private lateinit var server: MockRestServiceServer

    @Test
    fun getMeTest() {
        server.expect(requestTo("/getMe"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(getClassPathResource("telegramApi_getMe.json"), MediaType.APPLICATION_JSON))

        val response = api.getMe()

        assertThat(response).isNotNull
        assertThat(response.id).isEqualTo(192361356)
        assertThat(response.firstName).isEqualTo("DevBot")
        assertThat(response.username).isEqualTo("RuslanysBot")
    }

    @Test
    fun getUpdatesTest() {
        server.expect(requestTo("/getUpdates"))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withSuccess(getClassPathResource("telegramApi_getUpdates.json"), MediaType.APPLICATION_JSON))

        val response = api.getUpdates()

        assertThat(response).hasSize(2)
        assertThat(response[0].message!!.text).isEqualTo("Привет")
        assertThat(response[1].message!!.text).isEqualTo("Как дела?")
    }

    @Test
    fun setWebhookTest() {
        server.expect(requestTo("/setWebhook"))
                .andExpect(method(HttpMethod.POST))
                .andExpect(content().json("{\"url\": \"https://ruslanys.me/webhook\"}"))
                .andRespond(withSuccess(getClassPathResource("telegramApi_setWebhook.json"), MediaType.APPLICATION_JSON))

        val response = api.setWebhook("https://ruslanys.me/webhook")

        assertThat(response).isTrue()
    }

    @Test
    fun removeWebhookTest() {
        server.expect(requestTo("/setWebhook"))
                .andExpect(method(HttpMethod.POST))
                .andExpect(content().json("{\"url\": \"\"}"))
                .andRespond(withSuccess(getClassPathResource("telegramApi_removeWebhook.json"), MediaType.APPLICATION_JSON))

        val response = api.removeWebhook()

        assertThat(response).isTrue()
    }

    @Test
    fun sendMessageTest() {
        server.expect(requestTo("/sendMessage"))
                .andExpect(method(HttpMethod.POST))
                .andExpect(content().json("""
                    {
                      "chat_id": $CHAT_ID,
                      "text": "Привет, *Руслан*!",
                      "parse_mode": "MARKDOWN",
                      "disable_notification": false,
                      "disable_web_page_preview": false,
                      "reply_markup": {
                        "remove_keyboard": true,
                        "selective": false
                      }
                    }
                """.trimIndent(), true))
                .andRespond(withSuccess(getClassPathResource("telegramApi_sendMessage.json"), MediaType.APPLICATION_JSON))


        val request = TelegramMessageSendRequest(CHAT_ID, "Привет, *Руслан*!", TelegramParseMode.MARKDOWN)
        val response = api.sendMessage(request)

        assertThat(response.id).isEqualTo(5397)
        assertThat(response.text).isEqualTo("Привет, Руслан!")
    }

    @Test
    fun sendMessageWithAnswersTest() {
        server.expect(requestTo("/sendMessage"))
                .andExpect(method(HttpMethod.POST))
                .andExpect(content().json("""
                    {
                      "chat_id": $CHAT_ID,
                      "text": "Как дела, <i>Руслан</i>?",
                      "parse_mode": "HTML",
                      "disable_notification": false,
                      "disable_web_page_preview": false,
                      "reply_markup": {
                        "keyboard": [
                          ["Хорошо", "Пойдет"],
                          ["Отмена"]
                        ],
                        "resize_keyboard": true,
                        "one_time_keyboard": true,
                        "selective": false
                      }
                    }
                """.trimIndent(), true))
                .andRespond(withSuccess(getClassPathResource("telegramApi_sendMessageWithAnswers.json"), MediaType.APPLICATION_JSON))

        val request = TelegramMessageSendRequest(
                CHAT_ID,
                "Как дела, <i>Руслан</i>?",
                TelegramParseMode.HTML,
                TelegramMarkupReplyKeyboard(listOf("Хорошо", "Пойдет"))
        )
        val response = api.sendMessage(request)

        assertThat(response.id).isEqualTo(5399)
        assertThat(response.text).isEqualTo("Как дела, Руслан?")
    }

    @Test
    fun sendPhotoTest() {
        server.expect(requestTo("/sendPhoto"))
                .andExpect(method(HttpMethod.POST))
                .andExpect(content().contentTypeCompatibleWith(MediaType.MULTIPART_FORM_DATA))
                .andRespond(withSuccess(getClassPathResource("telegramApi_sendPhoto.json"), MediaType.APPLICATION_JSON))

        val request = TelegramPhotoSendRequest(
                CHAT_ID,
                getClassPathResource("telegramApi_photo.png").inputStream.readBytes(),
                "*Тестовая* картинка",
                TelegramParseMode.MARKDOWN
        )
        val response = api.sendPhoto(request)

        assertThat(response.id).isEqualTo(5404)
    }

    @Test
    fun sendVoiceTest() {
        server.expect(requestTo("/sendVoice"))
                .andExpect(method(HttpMethod.POST))
                .andExpect(content().contentTypeCompatibleWith(MediaType.MULTIPART_FORM_DATA))
                .andRespond(withSuccess(getClassPathResource("telegramApi_sendVoice.json"), MediaType.APPLICATION_JSON))

        val request = TelegramVoiceSendRequest(
                CHAT_ID,
                getClassPathResource("telegramApi_voice.mp3").inputStream.readBytes()
        )
        val response = api.sendVoice(request)

        assertThat(response.id).isEqualTo(5410)
    }

    private fun getClassPathResource(path: String): ClassPathResource {
        return ClassPathResource(path, javaClass)
    }

    companion object {
        private const val CHAT_ID = 73168307L
    }

    @Configuration
    class Config {

        @Bean
        fun telegramApi(restTemplateBuilder: RestTemplateBuilder): TelegramApi {
            return DefaultTelegramApi("key", restTemplateBuilder)
        }

    }

}