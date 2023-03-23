package joseluisgs.es.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import joseluisgs.es.repositories.NotesRepository

fun Application.notesRoutes() {
    routing {
        get("/notes") {
            call.respond(HttpStatusCode.OK, NotesRepository.getAllNotes())
        }
    }
}