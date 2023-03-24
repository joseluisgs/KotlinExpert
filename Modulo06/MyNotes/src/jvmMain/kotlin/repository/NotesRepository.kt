package repository

import data.api.notesRestClient
import io.ktor.client.call.*
import io.ktor.client.request.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import models.Note
import mu.KotlinLogging

private val logger = KotlinLogging.logger {}

object NotesRepository {
    // Lo trasformamos en un flujo, esta vez de listas de notas
    fun getNotes(): Flow<List<Note>> = flow {
        logger.debug { "[${Thread.currentThread().name}] -> Get Notas Remote" }
        val response = notesRestClient.request("http://localhost:8080/notes")
        emit(response.body())
    }
}
