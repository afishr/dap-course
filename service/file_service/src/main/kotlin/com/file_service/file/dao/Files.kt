package com.file_service.file.dao

import com.file_service.user.dao.Users
import org.jetbrains.exposed.sql.Table

object Files : Table() {
    val id = integer("id").autoIncrement()

    val path = varchar("path", 100)

    val userId = integer("usedId").uniqueIndex().references(Users.id)

    override val primaryKey = PrimaryKey(id)
}