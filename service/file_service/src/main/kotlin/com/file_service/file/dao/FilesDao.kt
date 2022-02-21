package com.file_service.file.dao

import com.example.model.File
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class FileDao(private val database : Database) : IFileDao {
    override fun init() = transaction(database){
        SchemaUtils.create(Files)
    }

    override fun createFile(path: String, userId: Int) = transaction(database) {
        Files.insert {
            it[Files.path] = path
            it[Files.userId] = userId
        }
        Unit
    }

    override fun updateFile(id: Int, path: String, userId: Int) = transaction(database) {
        Files.update({ Files.id eq id }) {
            it[Files.path] = path
            it[Files.userId] = userId
        }
        Unit
    }

    override fun deleteFile(id: Int) = transaction(database) {
        Files.deleteWhere { Files.id eq id }
        Unit
    }

    override fun getFile(id: Int): File? = transaction(database) {
        Files.select { Files.id eq id }.map {
            File(
                it[Files.id], it[Files.path], it[Files.userId]
            )
        }.singleOrNull()
    }

    override fun getAllFile(): List<File> = transaction(database) {
        Files.selectAll().map {
            File(
                it[Files.id], it[Files.path], it[Files.userId]
            )
        }
    }

    override fun close() {}

    private fun hikari(): HikariDataSource {
        val config = HikariConfig()
        config.driverClassName = "org.h2.Driver"
        config.jdbcUrl = "jdbc:h2:mem:test"
        config.maximumPoolSize = 3
        config.isAutoCommit = false
        config.transactionIsolation = "TRANSACTION_REPEATABLE_READ"
        config.validate()
        return HikariDataSource(config)
    }
}