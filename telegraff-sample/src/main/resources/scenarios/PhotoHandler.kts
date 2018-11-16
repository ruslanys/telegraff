import me.ruslanys.telegraff.core.dsl.handler
import me.ruslanys.telegraff.core.dto.request.PhotoMessage

handler("/photo", "фото") {

    process { _, _ ->
        val bytes = readClasspathResource("images/sample.png")
        PhotoMessage(bytes)
    }

}