package me.ruslanys.telegraff.sample

import me.ruslanys.telegraff.core.dto.request.MarkdownMessage
import me.ruslanys.telegraff.core.exception.ValidationException
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class TaxiHandlerTests : HandlerTests("/taxi") {

    @Test
    fun stepsTest() {
        assertThat(handler.getStepByKey("locationFrom")).isNotNull
        assertThat(handler.getStepByKey("locationTo")).isNotNull
        assertThat(handler.getStepByKey("paymentMethod")).isNotNull
        assertThat(handler.getStepByKey("123")).isNull()
    }

    @Test
    fun locationFromQuestionTest() {
        val step = getStep<String>("locationFrom")

        val question = step.question(state)

        assertThat(question).isEqualTo(MarkdownMessage("Откуда поедем?"))
    }

    @Test(expected = ValidationException::class)
    fun paymentMethodValidationWithExceptionTest() {
        val step = getStep<Any>("paymentMethod")

        step.validation("Payment method")
    }

    @Test
    fun paymentMethodValidationTest() {
        val step = getStep<Any>("paymentMethod")

        val answer = step.validation("картой")
        assertThat(answer).isNotNull
        assertThat(answer).isInstanceOf(Enum::class.java)
    }

    @Test
    fun processTest() {
        val paymentMethod = getStep<Any>("paymentMethod").validation("картой")

        val response = handler.process(state, mapOf(
                "locationFrom" to "Дом",
                "locationTo" to "Работа",
                "paymentMethod" to paymentMethod
        ))

        assertThat(response).isEqualTo(MarkdownMessage("""
            Заказ принят от пользователя #-1. 
            Поедем из Дом в Работа. Оплата CARD.
        """.trimIndent()))
    }

}