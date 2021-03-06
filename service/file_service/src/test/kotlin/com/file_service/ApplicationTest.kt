package com.file_service

import io.ktor.routing.*
import io.ktor.http.*
import io.ktor.serialization.*
import io.ktor.features.*
import io.ktor.gson.*
import io.ktor.jackson.*
import com.fasterxml.jackson.databind.*
import io.ktor.http.cio.websocket.*
import io.ktor.websocket.*
import java.time.*
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*
import kotlin.test.*
import io.ktor.server.testing.*
import com.file_service.plugins.*

class ApplicationTest {
    @Test
    fun testRoot() {
        withTestApplication({ configureRouting() }) {
            handleRequest(HttpMethod.Get, "/").apply {
                assertEquals(HttpStatusCode.OK, response.status())
                assertEquals("Hello World!", response.content)
            }
        }
    }
}