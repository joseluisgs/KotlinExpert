package services

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import models.Note
import mu.KotlinLogging

private val logger = KotlinLogging.logger {}

// Lo trasformamos en un flujo, esta vez de listas de notas
fun getNotes(): Flow<List<Note>> = flow {
    logger.debug { "[${Thread.currentThread().name}] -> get Notas" }
    delay(1000)
    val notes = (1..10).map {
        Note(
            title = "Title $it",
            description = "Description $it",
            type = if (it % 3 == 0) Note.Type.AUDIO else Note.Type.TEXT
        )
    }
    emit(notes)
}
