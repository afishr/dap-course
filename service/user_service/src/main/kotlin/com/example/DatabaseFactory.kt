package com.example

import com.example.dao.Users
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils.create
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.core.component.getScopeName

object DatabaseFactory {
    fun init(){
        Database.connect(hikari())
//        transaction {
//            create(Users)
//            Users.insert {
//                it[name] = "Ishrat Khan"
//                it[password]="root"
//            }
//            Users.insert {
//                it[name] = "Suhaib Roomy"
//                it[password]="root"
//            }
//        }
    }

    private fun hikari(): HikariDataSource {
        val config = HikariConfig()
        config.driverClassName = "org.postgresql.Driver"
        config.jdbcUrl = "jdbc:postgresql://localhost:26257/defaultdb"
        config.maximumPoolSize = 3
        config.isAutoCommit = false
        config.transactionIsolation = "TRANSACTION_REPEATABLE_READ"
        config.password = "root"
        config.username = "root"
        config.validate()
        return HikariDataSource(config)
    }
}