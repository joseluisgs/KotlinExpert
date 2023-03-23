package joseluisgs.es.routes

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.notesRoutes() {
    routing {
        get("/notes") {
            call.respondText("My Notes")
        }
    }
}