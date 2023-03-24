package joseluisgs.es.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import joseluisgs.es.models.Note
import joseluisgs.es.repositories.NotesRepository

fun Application.notesRoutes() {
    routing {
        route("notes") {
            // Get all notes
            get {
                call.respond(HttpStatusCode.OK, NotesRepository.getAllNotes())
            }
            // Get by title
            get("{id}") {
                val id = call.parameters["id"]?.toLongOrNull()
                id?.let {
                    val note = NotesRepository.getById(it)
                    note?.let { call.respond(HttpStatusCode.OK, note) }
                        ?: call.respond(HttpStatusCode.NotFound, "No se ha encontrado la nota con id $id")
                } ?: call.respond(HttpStatusCode.BadRequest, "Id no es válido")
            }

            // Post
            post {
                try {
                    val note = call.receive<Note>()
                    call.respond(HttpStatusCode.Created, NotesRepository.save(note))
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest, "Error al crear la nota")
                }
            }

            // Put
            put {
                try {
                    val note = call.receive<Note>()
                    val updatedNote = NotesRepository.update(note)
                    updatedNote?.let { call.respond(HttpStatusCode.OK, updatedNote) }
                        ?: call.respond(HttpStatusCode.NotFound, "No se ha encontrado la nota con id ${note.id}")
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest, "Error al actualizar la nota")
                }
            }

            // Delete
            delete("{id}") {
                val id = call.parameters["id"]?.toLongOrNull()
                id?.let {
                    val deleted = NotesRepository.delete(it)
                    if (deleted) call.respond(HttpStatusCode.NoContent)
                    else call.respond(HttpStatusCode.NotFound, "No se ha encontrado la nota con id $id")
                } ?: call.respond(HttpStatusCode.BadRequest, "Id no es válido")
            }
        }
    }
}