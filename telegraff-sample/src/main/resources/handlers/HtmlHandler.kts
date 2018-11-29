import me.ruslanys.telegraff.core.dsl.handler
import me.ruslanys.telegraff.core.dto.request.HtmlMessage

handler("/html") {

    process { _, _ ->
        HtmlMessage("Привет, <b>пользователь!</b>")
    }
    
}