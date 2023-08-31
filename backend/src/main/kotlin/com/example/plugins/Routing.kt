package com.example.plugins

import entities.Task
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.transactions.transaction
import responses.TaskResponse

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText("Hello World!")
        }

        get("/api/tasks") {
            val tasks: List<TaskResponse> = transaction {
                Task.all().map {
                    TaskResponse(
                        it.id.value,
                        it.title,
                        it.completed,
                        it.createdAt.toString("yyyy-MM-dd HH:mm:ss"),
                        it.updatedAt.toString("yyyy-MM-dd HH:mm:ss")
                    )
                }
            }
            println(tasks)
            call.respond(mapOf("data" to tasks))
        }
    }
}
