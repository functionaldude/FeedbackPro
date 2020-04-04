import kotlin.random.Random

class Feedback(val id: String, val description: String, val votes: MutableList<Vote>)
class Vote(val value: Int)

private val feedbacks = mutableListOf<Feedback>()

fun addFeedback(description: String) {
    feedbacks.add(
        Feedback(
            id = "feedback-${Random.nextInt()}",
            description = description,
            votes = mutableListOf()
        )
    )
}

fun getAllFeedbacks() = feedbacks
fun getFeedback(feedbackId: String) = feedbacks.find { it.id == feedbackId }

fun addVote(feedbackId: String, voteValue: Int) = getFeedback(feedbackId)?.votes?.add(Vote(value = voteValue))

fun getVoteCount(feedbackId: String): Int {
    return getFeedback(feedbackId)?.votes?.count() ?: throw IllegalArgumentException("Feedback not found")
}

fun getVotesAverage(feedbackId: String): Double {
    return getFeedback(feedbackId)?.votes?.map { it.value }?.average()
        ?: throw IllegalArgumentException("Feedback not found")
}