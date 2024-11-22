package com.fgiaquintaruiz.routes

import com.fgiaquintaruiz.models.User
import io.ktor.http.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

private val users = mutableListOf(
    User(1, "Brais Moure", 35, "braismoure@mouredev.com"),
    User(1, "MoureDev", 35, "mouredev@mouredev.com")
)

fun Route.userRouting() {
    route("/user") {
        get {
            if (users.isNotEmpty()) {
                call.respond(
                    users,
                    typeInfo = TODO()
                )
            } else {
                call.respondText("", status = HttpStatusCode.OK)
            }
        }
    }
}