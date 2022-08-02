package states

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import models.Note
import models.getNotes
import mu.KotlinLogging

private val logger = KotlinLogging.logger {}

// Y si lo pongo como object tendría un singleton
object AppState {
    var state by mutableStateOf(UiState())

    // Dos posibilidades, o nos creamos aquñi un scope  de corrutinas para consumilar o transformarla en suspend
    suspend fun loadNotes(coroutineScope: CoroutineScope) {
        //debemos hacerlo en un hilo aparte para no bloquear, hasta qu eveamos las corrutinas

        coroutineScope.launch {
            logger.debug { "Cargando notas" }
            state = UiState(isLoading = true)
            state = UiState(notes = getNotes(), isLoading = false)
            logger.debug { "Notas cargadas" }
        }


    }

    data class UiState(
        val isLoading: Boolean = false,
        val notes: List<Note>? = null //
    )
}

/*private fun AppState.UiState.update(function: () -> AppState.UiState) {
    state = function()
}*/