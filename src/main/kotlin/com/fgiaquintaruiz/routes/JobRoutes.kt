package com.fgiaquintaruiz.routes

import com.fgiaquintaruiz.repository.JobService
import io.ktor.http.*
import io.ktor.server.response.*
import io.ktor.server.routing.*


fun Route.jobRouting() {
//    route("/jobs") {
//        get {
//            val jobs = JobService().readAll()
//            if (!jobs.isNullOrEmpty()) {
//                call.respond(
//                    jobs
//                )
//            } else {
//                call.respondText("there aren't jobs", status = HttpStatusCode.OK)
//            }
//        }
//    }
}