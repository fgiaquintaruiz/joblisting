package com.fgiaquintaruiz.repository

import com.mongodb.client.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.config.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import mu.KotlinLogging

private val logger = KotlinLogging.logger {}

fun Application.configureDatabases() {
    val mongoDatabase = connectToMongoDB()
    val carService = CarService(mongoDatabase)
    val jobService = JobService(mongoDatabase)
    routing {
        // Create car
//        post("/cars") {
//            val car = call.receive<Car>()
//            val id = carService.create(car)
//            call.respond(HttpStatusCode.Created, id)
//        }
//        // Read car
//        get("/cars/{id}") {
//            val id = call.parameters["id"] ?: throw IllegalArgumentException("No ID found")
//            carService.read(id)?.let { car ->
//                call.respond(car)
//            } ?: call.respond(HttpStatusCode.NotFound)
//        }
//        // Update car
//        put("/cars/{id}") {
//            val id = call.parameters["id"] ?: throw IllegalArgumentException("No ID found")
//            val car = call.receive<Car>()
//            carService.update(id, car)?.let {
//                call.respond(HttpStatusCode.OK)
//            } ?: call.respond(HttpStatusCode.NotFound)
//        }
//        // Delete car
//        delete("/cars/{id}") {
//            val id = call.parameters["id"] ?: throw IllegalArgumentException("No ID found")
//            carService.delete(id)?.let {
//                call.respond(HttpStatusCode.OK)
//            } ?: call.respond(HttpStatusCode.NotFound)
//        }
        // Create job
        post("/jobs") {
            val job = call.receive<Job>()
            jobService.create(job)
            call.respond(HttpStatusCode.Created, job)
        }
        // Read job
        get("/jobs/{id}") {
            val id = call.parameters["id"]?.toInt() ?: throw IllegalArgumentException("No ID found")
            jobService.read(id)?.let { job ->
                call.respond(job)
            } ?: call.respond(HttpStatusCode.NotFound)
        }
        // Read all jobs
        get("/jobs") {
            jobService.readAll()?.let { jobs ->
                call.respond(jobs)
            } ?: call.respond(HttpStatusCode.NotFound)
        }
        // Update job
        put("/jobs/{id}") {
            val id = call.parameters["id"]?.toInt() ?: throw IllegalArgumentException("No ID found")
            val job = call.receive<Job>()
            jobService.update(id, job)?.let {
                call.respond(job)
            } ?: call.respond(HttpStatusCode.NotFound)
        }
        // Delete job
        delete("/jobs/{id}") {
            val id = call.parameters["id"]?.toInt() ?: throw IllegalArgumentException("No ID found")
            jobService.delete(id)?.let {
                call.respond(HttpStatusCode.OK)
            } ?: call.respond(HttpStatusCode.NotFound)
        }
    }
}

/**
 * Establishes connection with a MongoDB database.
 *
 * The following configuration properties (in application.yaml/application.conf) can be specified:
 * * `db.mongo.user` username for your database
 * * `db.mongo.password` password for the user
 * * `db.mongo.host` host that will be used for the database connection
 * * `db.mongo.port` port that will be used for the database connection
 * * `db.mongo.maxPoolSize` maximum number of connections to a MongoDB server
 * * `db.mongo.database.name` name of the database
 *
 * IMPORTANT NOTE: in order to make MongoDB connection working, you have to start a MongoDB server first.
 * See the instructions here: https://www.mongodb.com/docs/manual/administration/install-community/
 * all the paramaters above
 *
 * @returns [MongoDatabase] instance
 * */
fun Application.connectToMongoDB(): MongoDatabase {

    val user = environment.config.tryGetString("db.mongo.user")
    val password = environment.config.tryGetString("db.mongo.password")
    val host = environment.config.tryGetString("db.mongo.host") //?: "127.0.0.1"
    val port = environment.config.tryGetString("db.mongo.port") //?: "27017"
    val maxPoolSize = environment.config.tryGetString("db.mongo.maxPoolSize")?.toInt() ?: 20
    val databaseName = environment.config.tryGetString("db.mongo.database.name") ?: "myDatabase"

    logger.trace("user: {}, password: {}, host: {}, port: {}", user, password, host, port)
    val credentials = user?.let { userVal -> password?.let { passwordVal -> "$userVal:$passwordVal@" } }.orEmpty()
    val uri = "mongodb://$credentials$host:$port/?maxPoolSize=$maxPoolSize&w=majority"

    val mongoClient = MongoClients.create(uri)
    val database = mongoClient.getDatabase(databaseName)

    monitor.subscribe(ApplicationStopped) {
        mongoClient.close()
    }

    return database
}
