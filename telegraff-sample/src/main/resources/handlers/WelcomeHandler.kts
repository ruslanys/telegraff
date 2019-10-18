import me.ruslanys.telegraff.core.dsl.handler
import me.ruslanys.telegraff.core.dto.request.MarkdownMessage

handler("/start") {

    process { _, _ ->
        MarkdownMessage("Привет!")
    }
    
}