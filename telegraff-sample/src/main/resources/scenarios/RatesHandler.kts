package scenarios

import me.ruslanys.telegraff.core.dsl.handler
import me.ruslanys.telegraff.core.dto.request.MarkdownMessage

handler("/rates", "курс") {

    process { state, _ ->
        println(state.chat.username)

        MarkdownMessage("Текущий курс: 61 рубль за евро")
    }

}