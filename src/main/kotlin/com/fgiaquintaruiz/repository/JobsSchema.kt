package com.fgiaquintaruiz.repository

import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import com.mongodb.client.model.Filters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString
import org.bson.Document
import org.bson.types.ObjectId

@Serializable
data class Job(
    val id: Int,
    val type: String,
    val title: String,
    val description: String,
    val salary: String,
    val location: String,
    val company: Company
) {
    fun toDocument(): Document = Document.parse(Json.encodeToString(this))

    companion object {
        private val json = Json { ignoreUnknownKeys = true }

        fun fromDocument(document: Document): Job = json.decodeFromString(document.toJson())
    }
}

@Serializable
data class Company(
    val name: String,
    val description: String,
    val contactEmail: String,
    val contactPhone: String
) {
    fun toDocument(): Document = Document.parse(Json.encodeToString(this))

    companion object {
        private val json = Json { ignoreUnknownKeys = true }

        fun fromDocument(document: Document): Job = json.decodeFromString(document.toJson())
    }
}

class JobService(private val database: MongoDatabase) {
    var collection: MongoCollection<Document>

    init {
//        database.createCollection("jobs")
        collection = database.getCollection("jobs")
    }

    // Create new job
    suspend fun create(job: Job): Int = withContext(Dispatchers.IO) {
        val doc = job.toDocument()
        collection.insertOne(doc)
        doc["id"].toString().toInt()
    }

    // Read a job
    suspend fun read(id: Int): Job? = withContext(Dispatchers.IO) {
        collection.find(Filters.eq("id", id)).first()?.let(Job.Companion::fromDocument)
    }

    suspend fun readAll(): List<Job>? = withContext(Dispatchers.IO) {
        collection.find().map { doc ->
            val companyDoc = doc["company"] as Document
            val company = Company(
                companyDoc.getString("name"),
                companyDoc.getString("description"),
                companyDoc.getString("contactEmail"),
                companyDoc.getString("contactPhone")
            )
            // Assuming 'title' and 'company' fields exist in the Document
            Job(
                doc.getInteger("id"),
                doc.getString("type"),
                doc.getString("title"),
                doc.getString("description"),
                doc.getString("salary"),
                doc.getString("location"),
                company
            )
        }.toList()
    }

    // Update a job
    suspend fun update(id: Int, job: Job): Document? = withContext(Dispatchers.IO) {
        collection.findOneAndReplace(Filters.eq("id", id), job.toDocument())
    }

    // Delete a job
    suspend fun delete(id: Int): Document? = withContext(Dispatchers.IO) {
        collection.findOneAndDelete(Filters.eq("id", id))
    }
}

