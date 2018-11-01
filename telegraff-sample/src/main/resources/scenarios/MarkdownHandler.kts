import me.ruslanys.telegraff.core.dsl.handler
import me.ruslanys.telegraff.core.dto.request.MarkdownMessage

handler("/markdown") {

    process { _, _ ->
        MarkdownMessage("Привет, *пользователь*!")
    }

}