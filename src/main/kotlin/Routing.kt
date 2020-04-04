import html.generateVotesOverviewSite
import io.ktor.application.call
import io.ktor.http.ContentType
import io.ktor.request.receiveParameters
import io.ktor.response.respondRedirect
import io.ktor.response.respondText
import io.ktor.routing.Routing
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.route

fun Routing.applicationRouting() {
    route("/") {
        get("") {
            call.respondText(generateVotesOverviewSite(), ContentType.Text.Html)
        }
        get("vote/{feedbackId}/{voteValue}") {
            addVote(
                feedbackId = call.parameters["feedbackId"] ?: throw IllegalArgumentException("feedbackId is missing"),
                voteValue = call.parameters["voteValue"]?.toInt()
                    ?: throw IllegalArgumentException("voteValue is missing")
            )
            call.respondRedirect("/", permanent = false)
        }
        post("addFeedback") {
            val parameters = call.receiveParameters()
            addFeedback(
                description = parameters["feedbackDescription"]
                    ?: throw IllegalArgumentException("feedbackDescription is missing")
            )
            call.respondRedirect("/", permanent = false)
        }
    }
}