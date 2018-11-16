import me.ruslanys.telegraff.core.component.TelegramApi
import me.ruslanys.telegraff.core.dsl.handler
import me.ruslanys.telegraff.core.dto.request.MarkdownMessage
import me.ruslanys.telegraff.core.dto.request.TelegramMessageSendRequest
import me.ruslanys.telegraff.core.dto.request.TelegramParseMode
import me.ruslanys.telegraff.core.exception.ValidationException

handler("/counter") {

    step<Int>("counter") {

        question {
            MarkdownMessage("До скольки нужно посчитать?")
        }

        validation {
            try {
                it.toInt()
            } catch (e: Exception) {
                throw ValidationException("Укажите число")
            }
        }
    }

    process { state, answers ->
        val amount = answers["counter"] as Int

        val api = getBean<TelegramApi>()

        for (i in 1..amount) {
            val message = TelegramMessageSendRequest(
                    state.chat.id,
                    i.toString(),
                    TelegramParseMode.MARKDOWN
            )
            api.sendMessage(message)
        }

        null
    }

}