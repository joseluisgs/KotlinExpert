package services

import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import models.Note
import mu.KotlinLogging

private val logger = KotlinLogging.logger {}

// Vamos a usar un callback!! para que nos devuleva las notas
// Lo mejor es meterle y obligar a usarlo en una corrutina, indicándole el hilo en la funcion
// suspend, no bloquea, si se suspende mi mi pooler asigna mi hilo a otra cosa y cuando vuleva se lo paso a otro hilo
// Contexto, indicamos el polling de hilos que lo necesita, en este casoso los dispacher son de IO entrada y salida.
suspend fun getNotes() = withContext(Dispatchers.IO) {
    logger.debug { "[${Thread.currentThread().name}] [ ${this.coroutineContext[CoroutineName]} ] -> get Notas" }
    delay(1000)
    (1..10).map {
        Note(
            title = "Title $it",
            description = "Description $it",
            type = if (it % 3 == 0) Note.Type.AUDIO else Note.Type.TEXT
        )
    }.sortedByDescending { it.createdAt }
}
