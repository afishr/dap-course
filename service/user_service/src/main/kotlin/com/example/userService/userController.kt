package com.example.userService

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.example.dao.UsersDao
import com.example.model.User
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import kotlinx.serialization.json.buildJsonObject
import org.jetbrains.exposed.sql.Database
import java.util.*

fun Application.configureUserController(userService: UserService) {
    val secret = environment.config.property("ktor.jwt.secret").getString()
    val issuer = environment.config.property("ktor.jwt.issuer").getString()
    val audience = environment.config.property("ktor.jwt.audience").getString()
    val myRealm = environment.config.property("ktor.jwt.realm").getString()

    routing {
        route("/user") {
            post("/login") {
                val user = call.receive<User>()

                user.name?.let { name ->
                    val allUsers = userService.getAllUsers()

                    allUsers.findLast {
                        it.name == name
                    }?.let {
                        val token = JWT.create()
                            .withAudience(audience)
                            .withIssuer(issuer)
                            .withClaim("username", user.name)
                            .withExpiresAt(Date(System.currentTimeMillis() + 60000))
                            .sign(Algorithm.HMAC256(secret))

                        call.respond(hashMapOf("token" to token))
                    } ?: call.respond(HttpStatusCode.Unauthorized, "No such user")
                } ?: call.respond(HttpStatusCode.Unauthorized, "Please enter valid username")
            }

            get("/authenticate") {
                val token = call.request.headers["Authorization"]

                val decodedToken = JWT.decode(token)

                if(decodedToken.expiresAt.after(Date())) {
                    call.respond(HttpStatusCode.OK, "User authenticated")
                } else {
                    call.respond(HttpStatusCode.Unauthorized, "Unauthorized")
                }
            }
        }
    }

}