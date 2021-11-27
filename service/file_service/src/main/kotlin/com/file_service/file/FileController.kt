package com.file_service

import com.file_service.file.FileService
import io.ktor.application.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject
import java.util.logging.LogRecord
import java.util.logging.Logger

fun Application.configureFileController() {
    val fileService : FileService by inject()

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
