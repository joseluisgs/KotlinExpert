package es.joseluisgs.routes

import es.joseluisgs.models.Note
import es.joseluisgs.repositories.NotesRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.flow.toList
import org.koin.ktor.ext.inject

fun Application.notesRoutes() {
    // Inyectamos el repositorio de notas
    val notesRepository: NotesRepository by inject()

    routing {
        route("notes") {

            // Get all notes
            get {
                call.respond(HttpStatusCode.OK, notesRepository.getAllNotes().toList())
            }
            // Get by title
            get("{id}") {
                val id = call.parameters["id"]?.toLongOrNull()
                id?.let {
                    val note = notesRepository.getById(it).await()
                    note?.let { call.respond(HttpStatusCode.OK, note) }
                        ?: call.respond(HttpStatusCode.NotFound, "No se ha encontrado la nota con id $id")
                } ?: call.respond(HttpStatusCode.BadRequest, "Id no es válido")
            }

            // Post
            post {
                try {
                    val note = call.receive<Note>()
                    val savedNote = notesRepository.save(note).await()
                    call.respond(HttpStatusCode.Created, savedNote)
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest, "Error al crear la nota")
                }
            }

            // Put
            put {
                try {
                    val note = call.receive<Note>()
                    val updatedNote = notesRepository.update(note).await()
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
                    val isDeletedNote = notesRepository.delete(it).await()
                    if (isDeletedNote) call.respond(HttpStatusCode.NoContent)
                    else call.respond(HttpStatusCode.NotFound, "No se ha encontrado la nota con id $id")
                } ?: call.respond(HttpStatusCode.BadRequest, "Id no es válido")
            }
        }
    }
}