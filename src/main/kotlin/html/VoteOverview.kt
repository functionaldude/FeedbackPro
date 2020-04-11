package html

import getVoteOverviewSiteData
import kotlinx.html.*
import kotlinx.html.stream.appendHTML

fun generateVotesOverviewSite() = buildString {
    val siteData = getVoteOverviewSiteData()
    appendHTML().html {
        head {
            title(content = "FeedbackPro")
            unsafe {
                +"""
                    <link rel="stylesheet" type="text/css" href="https://cdnjs.cloudflare.com/ajax/libs/semantic-ui/2.4.1/semantic.min.css">
                    <script
                      src="https://code.jquery.com/jquery-3.1.1.min.js"
                      integrity="sha256-hVVnYaiADRTO2PzUGmuLJr8BLUSjGIZsDYGmIJLv2b8="
                      crossorigin="anonymous"></script>
                    <script src="https://cdnjs.cloudflare.com/ajax/libs/semantic-ui/2.4.1/semantic.min.js"></script>
                """.trimIndent()
            }
        }
        body {
            h1(classes = "ui header") { +"FeedbackPro" }
            table(classes = "ui celled table") {
                thead {
                    tr {
                        th { +"Description" }
                        th { +"Nr of votes" }
                        th { +"Vote result" }
                        th { +"Vote now!" }
                    }
                }
                tbody {
                    siteData.feedbacks.forEach { feedback ->
                        val voteStats = siteData.getVoteStats(feedback)
                        tr {
                            td { +feedback.description }
                            td { +voteStats.count.toString() }
                            td {
                                +if (voteStats.count > 0) voteStats.average.format(decimalDigits = 2) else "no votes yet!"
                            }
                            td {
                                div(classes = "ui buttons") {
                                    (1..5).forEach { index ->
                                        a(href = "/vote/${feedback._id}/$index") {
                                            button(classes = "ui button") { +index.toString() }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            div(classes = "ui divider")
            h3(classes = "ui header") { +"Add feedback!" }
            form(action = "/addFeedback", method = FormMethod.post) {
                div { +"Feedback description" }
                div(classes = "ui action input") {
                    input(type = InputType.text, name = "feedbackDescription")
                    button(classes = "ui button", type = ButtonType.submit) { +"Send" }
                }
            }

        }
    }
}

private fun Double.format(decimalDigits: Int): String = java.lang.String.format("%.${decimalDigits}f", this)
