package com.file_service

import com.file_service.file.FileService
import io.ktor.application.*
import org.koin.dsl.module
import org.koin.ktor.ext.Koin
import org.koin.logger.SLF4JLogger

fun Application.configureDependencyInjection() {
    val helloAppModule = module {
        single { FileService() } // get() Will resolve HelloRepository
    }

    // Declare Koin
    install(Koin) {
        SLF4JLogger()
        modules(helloAppModule)
    }
}