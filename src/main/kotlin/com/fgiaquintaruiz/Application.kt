package com.fgiaquintaruiz

import com.fgiaquintaruiz.repository.configureDatabases
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.netty.*

fun main(args: Array<String>) {
    EngineMain.main(args)
}

fun Application.module() {
//    intercept(ApplicationCallPipeline.Fallback) {
//        if (call.isHandled) return@intercept
//        val status = call.response.status() ?: HttpStatusCode.NotFound
//        call.respond(
//            status,
//            typeInfo = TODO()
//        )
//    }
    configureSerialization()
    configureDatabases()
    configureRouting()
}
