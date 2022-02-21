package com.file_service.file.dao

import com.example.model.File
import io.ktor.utils.io.core.*

interface IFileDao : Closeable {
    fun init()
    fun createFile(path: String, userId: Int)
    fun updateFile(id: Int, path: String, userId: Int)
    fun deleteFile(id: Int)
    fun getFile(id: Int): File?
    fun getAllFile(): List<File>
}