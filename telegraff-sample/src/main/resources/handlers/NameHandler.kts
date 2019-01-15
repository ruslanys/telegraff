import me.ruslanys.telegraff.core.dsl.handler
import me.ruslanys.telegraff.core.dto.request.MarkdownMessage
import me.ruslanys.telegraff.core.exception.ValidationException
import me.ruslanys.telegraff.sample.NameGenerator

handler("/name", "имя") {

    step<Int>("length") {
        question {
            MarkdownMessage("Какой длины?")
        }

        validation {
            try {
                Math.abs(it.toInt())
            } catch (e: Exception) {
                throw ValidationException("Укажи число")
            }
        }
    }


    process { state, answers ->
        val nameGenerator = getBean<NameGenerator>()

        val length = answers["length"] as Int
        val name = nameGenerator.generateName(length)

        MarkdownMessage("Сгенерированное имя: $name")
    }
}
