import me.ruslanys.telegraff.core.dsl.handler
import me.ruslanys.telegraff.core.dto.request.MarkdownMessage
import me.ruslanys.telegraff.core.exception.ValidationException
import me.ruslanys.telegraff.sample.NameGenerator

handler("/name") {

    step<Int>("length") {
        question {
            MarkdownMessage("Какой длинны?")
        }

        validation {
            try {
                it.toInt()
            } catch (e: Exception) {
                throw ValidationException(e.message ?: "ERROR")
            }
        }
    }


    process { state, answers ->
        val nameGenerator = getBean<NameGenerator>()

        val length = answers["length"] as Int
        val name = nameGenerator.generateName(length)

        MarkdownMessage("Твое новое имя $name")
    }
}