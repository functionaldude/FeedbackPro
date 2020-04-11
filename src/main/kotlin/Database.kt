import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import com.mongodb.client.model.Filters
import com.mongodb.client.model.Indexes
import org.bson.Document
import kotlin.random.Random

object Database {
    val mapper = ObjectMapper().registerKotlinModule()
    val mongoClient: MongoClient
    val database: MongoDatabase

    val feedbackCollection: MongoCollection<Document>
    val votesCollection: MongoCollection<Document>

    init {
        val connectionString = ConnectionString("mongodb://localhost:27017/feedbackPro")
        val settings = MongoClientSettings.builder()
            .applyConnectionString(connectionString)
            .retryWrites(true)
            .build()
        mongoClient = MongoClients.create(settings)
        database = mongoClient.getDatabase(connectionString.database!!)

        val collections = database.listCollectionNames().toSet()

        if ("feedbacks" !in collections) database.createCollection("feedbacks")
        feedbackCollection = database.getCollection("feedbacks")

        if ("votes" !in collections) database.createCollection("votes")
        votesCollection = database.getCollection("votes")
        votesCollection.createIndex(Indexes.ascending("feedbackId"))
    }
}

class Feedback(val _id: String, val description: String)
class Vote(val _id: String, val feedbackId: String, val value: Int)

fun addFeedback(description: String) {
    val feedback = Feedback(
        _id = "feedback ${Random.nextInt()}",
        description = description
    )
    Database.feedbackCollection.insertOne(Database.mapper.convertValue(feedback, Document::class.java))
}

fun getAllFeedbacks(): List<Feedback> {
    return Database.feedbackCollection
        .find()
        .toList()
        .map { Database.mapper.convertValue(it, Feedback::class.java) }
}

fun addVote(feedbackId: String, voteValue: Int) {
    val vote = Vote(
        _id = "vote $feedbackId ${Random.nextInt()}",
        feedbackId = feedbackId,
        value = voteValue
    )
    Database.votesCollection.insertOne(Database.mapper.convertValue(vote, Document::class.java))
}

fun getVoteCount(feedbackId: String): Int {
    return Database.votesCollection.countDocuments(Filters.eq("feedbackId", feedbackId)).toInt()
}

fun getVotesAverage(feedbackId: String): Double {
    return Database.votesCollection
        .find(Filters.eq("feedbackId", feedbackId))
        .toList()
        .map { Database.mapper.convertValue(it, Vote::class.java) }
        .map { it.value }
        .average()
}