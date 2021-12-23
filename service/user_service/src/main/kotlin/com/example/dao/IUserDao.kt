package com.example.dao

import com.example.model.User
import io.ktor.utils.io.core.*

interface IUserDao : Closeable {
    fun init()
    fun createUser(name: String, password: String)
    fun updateUser(id: Int, name: String, password: String)
    fun deleteUser(id: Int)
    fun getUser(id: Int): User?
    fun getAllUser(): List<User>
}