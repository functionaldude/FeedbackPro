package html

import getAllFeedbacks
import getVoteCount
import getVotesAverage
import kotlinx.html.*
import kotlinx.html.stream.appendHTML

fun generateVotesOverviewSite() = buildString {
    appendHTML().html {
        head {
            title(content = "FeedbackPro")
        }
        body {
            h1 { +"FeedbackPro" }
            p {
                table {
                    tr {
                        td { +"Description" }
                        td { +"Nr of votes" }
                        td { +"Vote result" }
                        td { +"Vote now!" }
                    }
                    getAllFeedbacks().forEach { feedback ->
                        tr {
                            td { +feedback.description }
                            td { +getVoteCount(feedbackId = feedback.id).toString() }
                            td {
                                +if (getVoteCount(feedbackId = feedback.id) > 0) getVotesAverage(feedbackId = feedback.id).toString() else "no votes yet!"
                            }
                            td {
                                a(href = "/vote/${feedback.id}/1") { +"1" }
                                +" "
                                a(href = "/vote/${feedback.id}/2") { +"2" }
                                +" "
                                a(href = "/vote/${feedback.id}/3") { +"3" }
                                +" "
                                a(href = "/vote/${feedback.id}/4") { +"4" }
                                +" "
                                a(href = "/vote/${feedback.id}/5") { +"5" }
                            }
                        }
                    }
                }
            }
            h3 { +"Add feedback!" }
            form(action = "/addFeedback", method = FormMethod.post) {
                div { +"Feedback description" }
                input(type = InputType.text, name = "feedbackDescription")
                button(type = ButtonType.submit) { +"Send" }
            }

        }
    }
}