class Vote(val value: Int)

private val votes = mutableListOf<Vote>()

fun addVote(vote: Vote) = votes.add(vote)
val voteCount get() = votes.count()
val votesAverage get() = votes.map { it.value }.average()