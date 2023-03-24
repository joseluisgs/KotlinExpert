package joseluisgs.es.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import joseluisgs.es.repositories.NotesRepository

fun Application.notesRoutes() {
    routing {
        // Get all notes
        get("/notes") {
            call.respond(HttpStatusCode.OK, NotesRepository.getAllNotes())
        }
        // Get by title
        get("/notes/:id") {
            val id = call.parameters["id"]?.toLongOrNull()
            id?.let {
                val note = NotesRepository.getById(it)
                note?.let { call.respond(HttpStatusCode.OK, note) }
                    ?: call.respond(HttpStatusCode.NotFound)
            } ?: call.respond(HttpStatusCode.BadRequest)
        }

    }
}