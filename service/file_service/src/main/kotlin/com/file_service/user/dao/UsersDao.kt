package com.example.dao

import com.example.model.User
import com.file_service.user.dao.Users
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class UsersDao(private val database : Database) : IUserDao{
    override fun init() = transaction(database){
        SchemaUtils.create(Users)
    }

    override fun createUser(name: String, password: String) = transaction(database){
        Users.insert {
            it[Users.name] = name
            it[Users.password] = password
        }
        Unit
    }

    override fun updateUser(id: Int, name: String, password: String) = transaction(database){
        Users.update({ Users.id eq id }) {
            it[Users.name] = name
            it[Users.password] = password
        }
        Unit
    }

    override fun deleteUser(id: Int) = transaction(database){
        Users.deleteWhere { Users.id eq id }
        Unit
    }

    override fun getUser(id: Int): User? = transaction(database) {
        Users.select { Users.id eq id }.map {
            User(
                it[Users.id], it[Users.name], it[Users.password]
            )
        }.singleOrNull()
    }

    override fun getAllUser(): List<User> = transaction(database) {
        Users.selectAll().map {
            User(
                it[Users.id], it[Users.name], it[Users.password]
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