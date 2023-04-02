package repository

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import config.AppConfig
import data.api.notesRestClient
import data.database.SqlDeLightClient
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import mappers.toNote
import models.Note
import mu.KotlinLogging

private val logger = KotlinLogging.logger {}

// Esta es la URL de la API de notas desde el cliente
private val NOTES_URL = AppConfig.NOTES_API_URL + "/notes"

object NotesRepository {
    // Lo trasformamos en un flujo, esta vez de listas de notas
    val notesDb = SqlDeLightClient.noteQueries
    val notesApi = notesRestClient

    init {
        logger.debug { "NotesRepository.init()" }
        val scope = CoroutineScope(Dispatchers.IO)
        scope.launch {
            // Limpiamos la base de datos
            rememoveAll()
            // Obtenemos las notas de la API
            fetchNotes()
        }

    }

    private suspend fun fetchNotes() = withContext(Dispatchers.IO) {
        // Llamamos a la API y obtenemos las notas
        logger.debug { "[${Thread.currentThread().name}] -> Get Notas Remote" }
        val response = notesApi.get(NOTES_URL)
        val notes = response.body<List<Note>>()

        // Actualizamos la base de datos
        logger.debug { "[${Thread.currentThread().name}] -> Insertando notas en la base de datos" }
        notes.forEach {
            notesDb.insert(
                id = it.id,
                title = it.title,
                description = it.description,
                type = it.type.name,
                created_at = it.createdAt.toString(),
            )
        }
    }

    private suspend fun rememoveAll() = withContext(Dispatchers.IO) {
        logger.debug { "[${Thread.currentThread().name}] -> Remove All Notes from DB" }
        notesDb.removeAll()
    }

    suspend fun getAll(): Flow<List<Note>> = withContext(Dispatchers.IO) {
        // Llamamos a la API
        /*logger.debug { "[${Thread.currentThread().name}] -> Get Notas Remote" }
        val response = notesApi.get(NOTES_URL)
        val notes = response.body<List<Note>>()
        // Actualizamos la base de datos
        logger.debug { "[${Thread.currentThread().name}] -> Insertando notas en la base de datos" }
            notes.forEach {
                notesDb.insert(
                    id = it.id,
                    title = it.title,
                    description = it.description,
                    type = it.type.name,
                    created_at = it.createdAt.toString(),
                )
            }*/

        // Obtenemos las notas de la base de datos
        logger.debug { "[${Thread.currentThread().name}] -> Select All from BD" }
        // Emitimos el flujo
        return@withContext notesDb.selectAll().asFlow().mapToList(Dispatchers.IO)
            .map { it.map { noteEntity ->  noteEntity.toNote() } }
    }

    // Estudiar lo de cambiar el tipo de retorno a Flow
    suspend fun getById(id: Long): Note =  withContext(Dispatchers.IO) {
        // logger.debug { "[${Thread.currentThread().name}] -> Get Nota remote con id:$id" }
        // val respose = notesRestClient.get("$NOTES_URL/$id")
        //return respose.body()

        // ya devolvemos la nota de la base de datos
        logger.debug { "[${Thread.currentThread().name}] -> Get Nota BD con id:$id" }
        return@withContext notesDb.selectById(id).executeAsOne().toNote()
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
        logger.debug { "[${Thread.currentThread().name}] -> Insertamos en BD" }
        notesDb.insert(
            id = savedNote.id,
            title = savedNote.title,
            description = savedNote.description,
            type = savedNote.type.name,
            created_at = savedNote.createdAt.toString(),
        )
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
        logger.debug { "[${Thread.currentThread().name}] -> Actualizamos en BD" }
        notesDb.update(
            id = updatedNote.id,
            title = updatedNote.title,
            description = updatedNote.description,
            type = updatedNote.type.name,
            created_at = updatedNote.createdAt.toString()
        )
        return@withContext updatedNote
    }

    suspend fun delete(id: Long): Boolean {
        // Llamamos a la API
        logger.debug { "[${Thread.currentThread().name}] -> Delete Nota Remote" }
        val response = notesApi.delete("$NOTES_URL/$id")

        // Eliminamos de la base de datos
        logger.debug { "[${Thread.currentThread().name}] -> Eliminamos de BD" }
        notesDb.delete(id)
        return response.status.value == 204
    }
}
