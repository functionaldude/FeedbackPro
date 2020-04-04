package html

import kotlinx.html.*
import kotlinx.html.stream.appendHTML
import voteCount
import votesAverage

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
                        td { +"Nr of votes" }
                        td { +voteCount.toString() }
                    }
                    tr {
                        td { +"Vote result" }
                        td {
                            +if (voteCount > 0) votesAverage.toString() else "no votes yet!"
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