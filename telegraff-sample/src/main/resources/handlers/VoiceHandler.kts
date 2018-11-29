import me.ruslanys.telegraff.core.dsl.handler
import me.ruslanys.telegraff.core.dto.request.VoiceMessage

handler("/voice", "voice") {

    process { _, _ ->
        val bytes = readClasspathResource("audio/sample.mp3")
        VoiceMessage(bytes)
    }

}