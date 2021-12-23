package com.example.plugins

import com.fasterxml.jackson.databind.SerializationFeature
import io.ktor.serialization.*
import io.ktor.features.*
import io.ktor.application.*
import io.ktor.jackson.*
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.routing.*

fun Application.configureSerialization() {

    install(ContentNegotiation) {
        jackson {
            configure(SerializationFeature.INDENT_OUTPUT, true)
        }
    }

    routing {
        get("/json/kotlinx-serialization") {
            call.respond(mapOf("hello" to "world"))
        }
    }
}
