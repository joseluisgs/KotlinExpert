package repository

import data.api.notesRestClient
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import models.Note
import mu.KotlinLogging

private val logger = KotlinLogging.logger {}

private const val NOTES_URL = "http://localhost:8080/notes"

object NotesRepository {
    // Lo trasformamos en un flujo, esta vez de listas de notas
    fun getAll(): Flow<List<Note>> = flow {
        logger.debug { "[${Thread.currentThread().name}] -> Get Notas Remote" }
        val response = notesRestClient.request(NOTES_URL)
        emit(response.body())
    }

    // Estudiar lo de cambiar el tipo de retorno a Flow
    suspend fun getById(id: Long): Note {
        logger.debug { "[${Thread.currentThread().name}] -> Get Nota Remote con id:$id" }
        val respose = notesRestClient.get("$NOTES_URL/$id")
        return respose.body()
    }

    suspend fun save(note: Note): Note {
        logger.debug { "[${Thread.currentThread().name}] -> Create Nota Remote" }
        val response = notesRestClient.post(NOTES_URL) {
            setBody(note)
            contentType(ContentType.Application.Json)
        }
        return response.body()
    }

    suspend fun update(note: Note): Note {
        logger.debug { "[${Thread.currentThread().name}] -> Update Nota Remote" }
        val response = notesRestClient.put(NOTES_URL) {
            setBody(note)
            contentType(ContentType.Application.Json)
        }
        return response.body()
    }

    suspend fun delete(id: Long): Boolean {
        logger.debug { "[${Thread.currentThread().name}] -> Delete Nota Remote" }
        val response = notesRestClient.delete("$NOTES_URL/$id")
        return response.status.value == 204
    }
}
