package repository

import data.api.provideNotesRestClient
import data.cache.provideNotesCacheClient
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import models.Note
import mu.KotlinLogging

private val logger = KotlinLogging.logger {}

// Esta es la URL de la API de notas desde el cliente
private val NOTES_URL = config.AppConfig.NOTES_API_URL + "/notes"

object NotesRepository {
    // Lo trasformamos en un flujo, esta vez de listas de notas
    val notesCache = provideNotesCacheClient()
    val notesApi = provideNotesRestClient()

    init {
        logger.debug { "NotesRepository.init()" }
        val scope = CoroutineScope(Dispatchers.IO)
        scope.launch {
            // Limpiamos la base de datos
            rememoveAll()
            // Obtenemos las notas de la API
            //fetchNotes()
        }

    }

    private suspend fun fetchNotes() = withContext(Dispatchers.IO) {
        // Llamamos a la API y obtenemos las notas
        logger.debug { "[${Thread.currentThread().name}] -> Get Notas Remote" }
        val response = notesApi.get(NOTES_URL)
        val notes = response.body<List<Note>>()

        // Actualizamos la base de datos
        logger.debug { "[${Thread.currentThread().name}] -> Insertando notas en la cache" }
        notes.forEach {
            notesCache.put(it.id, it)
        }
    }

    private suspend fun rememoveAll() = withContext(Dispatchers.IO) {
        logger.debug { "[${Thread.currentThread().name}] -> Remove All Notes from DB" }
        notesCache.invalidateAll()
    }

    suspend fun getAll(): Flow<List<Note>> = withContext(Dispatchers.IO) {
        // Si no hay notas en la base de datos las obtenemos de la API
        if (notesCache.asMap().isEmpty()) {
            logger.debug { "[${Thread.currentThread().name}] -> No hay notas en la cache" }
            fetchNotes()
        }
        // Emitimos el flujo
        logger.debug { "[${Thread.currentThread().name}] -> Emitimos el flujo" }
        return@withContext flowOf(notesCache.asMap().values.toList())

    }

    // Estudiar lo de cambiar el tipo de retorno a Flow
    suspend fun getById(id: Long): Note = withContext(Dispatchers.IO) {
        // Devolvemos de la cache
        logger.debug { "[${Thread.currentThread().name}] -> Get Nota by Id from Cache" }
        return@withContext notesCache.get(id)!!
    }

    suspend fun save(note: Note): Note = withContext(Dispatchers.IO) {
        // Llamamos a la API
        logger.debug { "[${Thread.currentThread().name}] -> Create Nota Remote" }
        val response = notesApi.post(NOTES_URL) {
            setBody(note)
            contentType(ContentType.Application.Json)
        }
        val savedNote = response.body<Note>()

        // Insertamos en la base de datos
        logger.debug { "[${Thread.currentThread().name}] -> Insertamos en Cache" }
        notesCache.put(savedNote.id, savedNote)
        return@withContext savedNote
    }

    suspend fun update(note: Note): Note = withContext(Dispatchers.IO) {
        // Llamamos a la API
        logger.debug { "[${Thread.currentThread().name}] -> Update Nota Remote" }
        val response = notesApi.put(NOTES_URL) {
            setBody(note)
            contentType(ContentType.Application.Json)
        }
        val updatedNote = response.body<Note>()

        // Actualizamos en la base de datos
        logger.debug { "[${Thread.currentThread().name}] -> Actualizamos en Cache" }
        notesCache.put(updatedNote.id, updatedNote)
        return@withContext updatedNote
    }

    suspend fun delete(id: Long): Boolean {
        // Llamamos a la API
        logger.debug { "[${Thread.currentThread().name}] -> Delete Nota Remote" }
        val response = notesApi.delete("$NOTES_URL/$id")

        // Eliminamos de la base de datos
        logger.debug { "[${Thread.currentThread().name}] -> Eliminamos de Cache" }
        notesCache.invalidate(id)
        return response.status.value == 204
    }
}
