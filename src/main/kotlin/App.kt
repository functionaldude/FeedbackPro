import io.ktor.application.call
import io.ktor.http.ContentType
import io.ktor.response.respondRedirect
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.route
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import kotlinx.html.*
import kotlinx.html.stream.appendHTML

class Vote(val value: Int)

val votes = mutableListOf<Vote>()

fun main(args: Array<String>) {
    embeddedServer(Netty, 8080) {
        routing {
            route("/") {
                get("") {
                    val html = buildString {
                        appendHTML().html {
                            head {
                                title(content = "FeedbackPro")
                            }
                            body {
                                h1 { +"FeedbackPro" }
                                p {
                                    table {
                                        tr {
                                            td { +"Nr of votes" }
                                            td { +votes.count().toString() }
                                        }
                                        tr {
                                            td { +"Vote result" }
                                            td {
                                                +if (votes.count() > 0) votes.map { it.value }.average()
                                                    .toString() else "no votes yet!"
                                            }
                                        }
                                    }
                                }
                                h3 { +"Vote now" }
                                p {
                                    div { a(href = "/vote/1") { +"1" } }
                                    div { a(href = "/vote/2") { +"2" } }
                                    div { a(href = "/vote/3") { +"3" } }
                                    div { a(href = "/vote/4") { +"4" } }
                                    div { a(href = "/vote/5") { +"5" } }
                                }
                            }
                        }
                    }
                    call.respondText(html, ContentType.Text.Html)
                }
                get("vote/{voteValue}") {
                    votes.add(Vote(value = call.parameters["voteValue"]!!.toInt()))
                    call.respondRedirect("/", permanent = false)
                }
            }
        }
    }.start(wait = true)
}