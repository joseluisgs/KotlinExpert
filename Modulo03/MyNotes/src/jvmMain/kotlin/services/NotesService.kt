package services

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import models.Note
import mu.KotlinLogging

private val logger = KotlinLogging.logger {}

// Lo trasformamos en un flujo
fun getNotes(): Flow<Note> = flow {
    logger.debug { "[${Thread.currentThread().name}] -> get Notas" }
    delay(1000)
    (1..10).forEach {
        emit(
            Note(
                title = "Title $it",
                description = "Description $it",
                type = if (it % 3 == 0) Note.Type.AUDIO else Note.Type.TEXT
            )
        )
        delay(500) // Para ver como se va recomponiendo o pintando reactivamente poco a poco
    }
}
