package com.file_service

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.file_service.plugins.configureHTTP
import com.file_service.plugins.configureRouting
import com.file_service.plugins.configureSerialization
import com.file_service.plugins.configureSockets
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.netty.*

fun main(args: Array<String>): Unit = EngineMain.main(args)

fun Application.module(testing: Boolean = false) {
    DatabaseFactory.init()

    val secret = "secret"
    val issuer = "http://0.0.0.0:8084/"
    val audience = "http://0.0.0.0:8084/hello"
    val realm = "Access to 'hello'"

    install(Authentication) {
        jwt("auth-jwt") {
            this.realm = realm

            verifier(
                JWT
                    .require(Algorithm.HMAC256(secret))
                    .withAudience(audience)
                    .withIssuer(issuer)
                    .build()
            )
        }
    }
    configureDependencyInjection()
    configureRouting()
    configureSerialization()
    configureSockets()
    configureHTTP()
    configureFileController()

    routing {
        get("/") {
            call.respondText("Hello, world!")
        }
    }
}