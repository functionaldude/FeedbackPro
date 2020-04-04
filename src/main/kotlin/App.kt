import io.ktor.application.call
import io.ktor.http.ContentType
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import kotlinx.html.*
import kotlinx.html.stream.appendHTML

fun main(args: Array<String>) {
    embeddedServer(Netty, 8080) {
        routing {
            get("/") {
                val html = buildString {
                    appendHTML().html {
                        head {
                            title(content = "FeedbackPro")
                        }
                        body {
                            h1 { +"FeedbackPro" }
                        }
                    }
                }
                call.respondText(html, ContentType.Text.Html)
            }
        }
    }.start(wait = true)
}