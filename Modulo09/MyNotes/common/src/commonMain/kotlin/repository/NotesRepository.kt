package repository

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import data.api.NOTES_URL
import data.api.provideNotesRestClient
import data.cache.provideNotesCacheClient
import errors.NoteError
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import models.Note
import org.lighthousegames.logging.logging

// Esta es la URL de la API de notas desde el cliente


private val logger = logging()

object NotesRepository {
    // Lo trasformamos en un flujo, esta vez de listas de notas
    private val notesCache = provideNotesCacheClient()
    private val notesApi = provideNotesRestClient()

    init {
        logger.info { "NotesRepository Init" }
        val scope = CoroutineScope(Dispatchers.Default)
        scope.launch {
            // Limpiamos la base de datos
            removeAll()
            // Obtenemos las notas de la API
            //fetchNotes()
        }

    }

    private suspend fun fetchNotes(): Result<List<Note>, NoteError> {
        logger.debug { "fetchNotes" }
        // Llamamos a la API y obtenemos las notas
        return try {
            val response = notesApi.get(NOTES_URL)
            val notes = response.body<List<Note>>()

            // Actualizamos la base de datos
            notes.forEach {
                notesCache.put(it.id, it)
            }
            logger.debug { "fetchNotes(${notes.size}: $notes" }
            Ok(notes)
        } catch (e: Exception) {
            logger.error { "fetchNotes: ${e.message}" }
            Err(NoteError.ApiError(e.message ?: "Error al obtener las notas de la API de online"))
        }

    }

    private fun removeAll() {
        logger.debug { "removeAll" }
        notesCache.invalidateAll()
    }

    suspend fun getAll(): Result<Flow<List<Note>>, NoteError> {
        logger.debug { "getAll" }
        // Si no hay notas en la base de datos las obtenemos de la API
        if (notesCache.asMap().isNotEmpty()) {
            return Ok(flowOf(notesCache.asMap().values.toList()))
        }
        // Emitimos el flujo
        return when (val result = fetchNotes()) {
            is Ok -> Ok(flowOf(result.value))
            is Err -> Err(result.error)
        }
    }

    // Estudiar lo de cambiar el tipo de retorno a Flow
    fun getById(id: Long): Note {
        logger.debug { "getById" }
        // Devolvemos de la cache
        return notesCache.get(id)!!
    }

    suspend fun save(note: Note): Note {
        logger.debug { "save" }
        // Llamamos a la API
        val response = notesApi.post(NOTES_URL) {
            setBody(note)
            contentType(ContentType.Application.Json)
        }
        val savedNote = response.body<Note>()

        // Insertamos en la base de datos
        notesCache.put(savedNote.id, savedNote)
        return savedNote
    }

    suspend fun update(note: Note): Note {
        logger.debug { "update" }
        // Llamamos a la API
        val response = notesApi.put(NOTES_URL) {
            setBody(note)
            contentType(ContentType.Application.Json)
        }
        val updatedNote = response.body<Note>()

        // Actualizamos en la base de datos
        notesCache.put(updatedNote.id, updatedNote)
        return updatedNote
    }

    suspend fun delete(id: Long): Boolean {
        logger.debug { "delete" }
        // Llamamos a la API
        val response = notesApi.delete("$NOTES_URL/$id")

        // Eliminamos de la base de datos
        notesCache.invalidate(id)
        return response.status.value == 204
    }
}
