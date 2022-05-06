package com.example

import entities.ToDo
import entities.ToDoDraft
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.gson.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.routing.*
import repository.InMemoryToDoRepository
import repository.MySqLTodoRepository
import repository.ToDoRepository

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {

    install(CallLogging)
    install(ContentNegotiation) {
        gson {
            setPrettyPrinting()
        }
    }
    routing {
        val repo: ToDoRepository = MySqLTodoRepository()

        get("/") {
            call.respondText("Hello New Server")
        }
        get("/todos") {
            call.respond(repo.getAllToDos())
        }

        get("/todos/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            if (id == null) {
                call.respond(
                    HttpStatusCode.BadRequest,
                    "Id parameter has to be a number"
                )
                return@get
            }
            val todo = repo.getToDo(id)
            if (todo == null) {
                call.respond(
                    HttpStatusCode.NotFound,
                    "Note not Found"
                )
            } else {
                call.respond(todo)
            }
            call.respondText("TodoList Details for Todo Item #$todo ")
        }

        post("/todos") {
            val todoDraft = call.receive<ToDoDraft>()
            val todo = repo.addToDo(todoDraft)
            call.respond(todo)
        }

        put("/todos/{id}") {
            val todoDraft = call.receive<ToDoDraft>()
            val todoId = call.parameters["id"]?.toIntOrNull()
            if (todoId == null) {
                call.respond(
                    HttpStatusCode.BadRequest,
                    "Id parameter has to be a number!"
                )
                return@put
            }

            val updated = repo.updateToDo(todoId, todoDraft)
            if (updated) {
                call.respond(HttpStatusCode.OK)
            } else {
                call.respond(
                    HttpStatusCode.NotFound,
                    "No Todo Found with this id $todoId"
                )

            }

        }

        delete("/todos/{id}") {
            val todoId = call.parameters["id"]?.toIntOrNull()
            if (todoId == null) {
                call.respond(
                    HttpStatusCode.BadRequest,
                    "Id parameter has to be a number!"
                )
                return@delete
            }
            val remove = repo.removeToDo(todoId)
            if (remove) {
                call.respond(HttpStatusCode.OK)
            } else {
                call.respond(
                    HttpStatusCode.NotFound,
                    "No Todo Found with this id $todoId"
                )
            }
        }
    }
}

