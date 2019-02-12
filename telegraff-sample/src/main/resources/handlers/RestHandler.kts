import me.ruslanys.telegraff.core.dsl.handler
import me.ruslanys.telegraff.core.dto.request.MarkdownMessage
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.web.client.getForObject

data class HttpbinResponse(
        val headers: Map<String, String>,
        val args: Map<String, String>,
        val origin: String,
        val url: String
)

handler("/rest") {

    val rest = RestTemplateBuilder()
            .rootUri("https://httpbin.org")
            .build()

    process { _, _ ->
        val response = rest.getForObject<HttpbinResponse>("/get")!!

        MarkdownMessage("Your IP: ${response.origin}")
    }

}