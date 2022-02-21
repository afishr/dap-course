package com.file_service

import com.example.model.User
import com.file_service.file.FileService
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import io.ktor.routing.*
import io.ktor.sessions.*
import org.koin.ktor.ext.inject

fun Application.configureFileController() {
    val fileService: FileService by inject()

    routing {
        route("/file") {
            post("/upload") {
                fileService.uploadFile(call)
            }

            get("/{name}") {
                fileService.downloadFile(call)
            }
        }
    }
}
