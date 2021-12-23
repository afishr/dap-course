package com.example

import com.example.plugins.*
import com.example.userService.UserService
import com.example.userService.configureUserController
import com.fasterxml.jackson.databind.SerializationFeature
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.jackson.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.netty.*

fun main(args: Array<String>): Unit = EngineMain.main(args)

fun Application.module(testing: Boolean = false) {
    DatabaseFactory.init()

    configureRouting()
    configureSecurity()
    configureHTTP()
    configureMonitoring()
    configureSerialization()
    configureUserController(UserService())

    routing {
        get("/") {
            call.respondText("Hello, world!")
        }
    }
}
