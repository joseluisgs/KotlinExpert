package repository

import data.api.provideNotesRestClient
import data.cache.provideNotesCacheClient
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import models.Note

// Esta es la URL de la API de notas desde el cliente
private val NOTES_URL = "http://localhost:8080/notes" //config.AppConfig.NOTES_API_URL + "/notes"

object NotesRepository {
    // Lo trasformamos en un flujo, esta vez de listas de notas
    val notesCache = provideNotesCacheClient()
    val notesApi = provideNotesRestClient()

    init {
        val scope = CoroutineScope(Dispatchers.Default)
        scope.launch {
            // Limpiamos la base de datos
            rememoveAll()
            // Obtenemos las notas de la API
            //fetchNotes()
        }

    }

    private suspend fun fetchNotes() {
        // Llamamos a la API y obtenemos las notas
        val response = notesApi.get(NOTES_URL)
        val notes = response.body<List<Note>>()

        // Actualizamos la base de datos
        notes.forEach {
            notesCache.put(it.id, it)
        }
    }

    private suspend fun rememoveAll() {
        notesCache.invalidateAll()
    }

    suspend fun getAll(): Flow<List<Note>> {
        // Si no hay notas en la base de datos las obtenemos de la API
        if (notesCache.asMap().isEmpty()) {
            fetchNotes()
        }
        // Emitimos el flujo
        return flowOf(notesCache.asMap().values.toList())

    }

    // Estudiar lo de cambiar el tipo de retorno a Flow
    suspend fun getById(id: Long): Note {
        // Devolvemos de la cache
        return notesCache.get(id)!!
    }

    suspend fun save(note: Note): Note {
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
        // Llamamos a la API
        val response = notesApi.delete("$NOTES_URL/$id")

        // Eliminamos de la base de datos
        notesCache.invalidate(id)
        return response.status.value == 204
    }
}
