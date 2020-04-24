import io.ktor.application.call
import io.ktor.response.respondRedirect
import io.ktor.routing.get
import io.ktor.routing.route
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty

fun main(args: Array<String>) {
    embeddedServer(Netty, port = System.getenv("PORT")?.toIntOrNull() ?: 8080) {
        routing {
            get("") {
                call.respondRedirect("/feedbackpro")
            }
            route("feedbackpro") {
                applicationRouting()
            }
        }
    }.start(wait = true)
}