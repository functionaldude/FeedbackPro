import html.generateVotesOverviewSite
import io.ktor.application.call
import io.ktor.http.ContentType
import io.ktor.response.respondRedirect
import io.ktor.response.respondText
import io.ktor.routing.Routing
import io.ktor.routing.get
import io.ktor.routing.route

fun Routing.applicationRouting() {
    route("/") {
        get("") {
            call.respondText(generateVotesOverviewSite(), ContentType.Text.Html)
        }
        get("vote/{voteValue}") {
            addVote(Vote(value = call.parameters["voteValue"]!!.toInt()))
            call.respondRedirect("/", permanent = false)
        }
    }
}