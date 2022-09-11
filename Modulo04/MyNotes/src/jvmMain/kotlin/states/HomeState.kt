package states

import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import models.Note
import mu.KotlinLogging
import services.getNotes

private val logger = KotlinLogging.logger {}

// Y si lo pongo como object tendría un singleton
object HomeState {
    // Estados privados mutables, públicos inmutables. Paso a Flow
    private val _state = MutableStateFlow(UiState())
    val state = _state.asStateFlow()

    // Dos posibilidades, o nos creamos aquñi un scope  de corrutinas para consumilar o transformarla en suspend
    suspend fun loadNotes(coroutineScope: CoroutineScope) {
        //debemos hacerlo en un hilo aparte para no bloquear, hasta qu eveamos las corrutinas

        coroutineScope.launch(CoroutineName("Corrutina loadNotes ")) {
            logger.debug { "[${Thread.currentThread().name}] [ ${this.coroutineContext[CoroutineName]} ] -> Cargando notas" }
            _state.value = UiState(isLoading = true)
            getNotes().collect {
                logger.debug { "\"[${Thread.currentThread().name}] [ ${this.coroutineContext[CoroutineName]} ] -> Consumiendo Notas" }
                // cada vez que recibimos un valor se lo sumamos a la lista original
                // Actualizamos la interfaz
                _state.value = UiState(notes = it, isLoading = false)
            }
            logger.debug { "\"[${Thread.currentThread().name}] [ ${this.coroutineContext[CoroutineName]} ] -> Notas cargadas" }
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
