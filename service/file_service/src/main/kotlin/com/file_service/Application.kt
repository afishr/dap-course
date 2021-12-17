package com.file_service

import com.file_service.plugins.configureHTTP
import com.file_service.plugins.configureRouting
import com.file_service.plugins.configureSerialization
import com.file_service.plugins.configureSockets
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main() {
    embeddedServer(Netty, port = 3050, host = "0.0.0.0") {
        configureDependencyInjection()
        configureRouting()
        configureSerialization()
        configureSockets()
        configureHTTP()
        configureFileController()
    }.start(wait = true)
}
