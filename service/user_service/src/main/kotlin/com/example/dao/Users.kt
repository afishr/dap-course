package com.example.dao

import org.jetbrains.exposed.sql.Table

object Users : Table() {
    val id = integer("id").autoIncrement()

    val name = varchar("name", 100)

    val password = varchar("password", 100)

    override val primaryKey = PrimaryKey(id)
}