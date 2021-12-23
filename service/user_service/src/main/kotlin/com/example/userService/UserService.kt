package com.example.userService

import com.example.dao.Users
import com.example.dao.Users.name
import com.example.model.User
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class UserService {
    suspend fun getUserByName(userName: String) : User = newSuspendedTransaction {
        Users.select {
            name eq userName
        }.limit(1).single().let {
            toUsers(it)
        }
    }

    suspend fun getAllUsers() : List<User> = newSuspendedTransaction{
        Users.selectAll().map { toUsers(it) }
    }

    private fun toUsers(row:ResultRow) : User {
        return User(
            id = row[Users.id],
            name = row[Users.name],
            password = row[Users.password]
        )
    }
}