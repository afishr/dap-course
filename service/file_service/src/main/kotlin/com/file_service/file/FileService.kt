package com.file_service.file

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.request.*
import io.ktor.response.*
import java.io.File

class FileService {
    companion object {
        const val FILE_DIRECTORY_PATH = "/Users/pavelbugaian/ktor/uploads/"
    }

    suspend fun uploadFile(call: ApplicationCall) {
        val multipart = call.receiveMultipart()

        multipart.forEachPart { part ->

            // if part is a file (could be form item)
            if (part is PartData.FileItem) {

                // retrieve file name of upload
                val name = part.originalFileName!!
                val file = File("$FILE_DIRECTORY_PATH$name")

                // use InputStream from part to save file
                part.streamProvider().use { its ->

                    // copy the stream to the file with buffering
                    file.outputStream().buffered().use {

                        // note that this is blocking
                        its.copyTo(it)
                    }
                }
            }

            // make sure to dispose of the part after use to prevent leaks
            part.dispose()
        }
    }

    suspend fun downloadFile(call: ApplicationCall) {

        // get filename from request url
        val filename = call.parameters["name"]!!

        // construct reference to file
        // ideally this would use a different filename
        val file = File("$FILE_DIRECTORY_PATH$filename")

        if (file.exists()) {
            call.respondFile(file)
        } else {
            call.respond(HttpStatusCode.NotFound)
        }
    }
}