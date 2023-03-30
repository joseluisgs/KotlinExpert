package views.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import models.Filter
import models.Note
import mu.KotlinLogging
import repository.NotesRepository


private val logger = KotlinLogging.logger {}

class HomeViewModel(private val scope: CoroutineScope) {
    // podemos usar StateFlow o MutableState
    // Estados privados mutables, públicos inmutables. Paso a Flow para estado compartido
    //private val _state = MutableStateFlow(UiState())
    // val state = _state.asStateFlow()

    // Vamos a usar MutableState de Compose
    var state by mutableStateOf(UiState())
        private set

    init {
        // Nada más cargar el viewModel, cargamos las notas
        logger.debug { "HomeViewModel.init()" }

        loadNotes()
    }

    private fun removeNotes() {
        scope.launch(CoroutineName("Corrutina removeNotes ")) {
            logger.debug { "[${Thread.currentThread().name}] [ ${this.coroutineContext[CoroutineName]} ] -> Borrando notas" }
            NotesRepository.rememoveAll()
            logger.debug { "[${Thread.currentThread().name}] [ ${this.coroutineContext[CoroutineName]} ] -> Notas borradas" }
        }
    }


    private fun loadNotes() {
        scope.launch(CoroutineName("Corrutina loadNotes ")) {
            logger.debug { "[${Thread.currentThread().name}] [ ${this.coroutineContext[CoroutineName]} ] -> Cargando notas" }
            state = UiState(isLoading = true)

            // Elimino las notas
            logger.debug { "[${Thread.currentThread().name}] [ ${this.coroutineContext[CoroutineName]} ] -> Borrando notas" }
            removeNotes()

            // Creo las notas
            logger.debug { "[${Thread.currentThread().name}] [ ${this.coroutineContext[CoroutineName]} ] -> Consumiendo las notas" }
            /*NotesRepository.getAll().collect {
                // Actualizamos la interfaz y estado
                state = state.copy(notes = it, isLoading = false)
            }*/

            // Otra forma de hacerlo
            state = state.copy(notes = NotesRepository.getAll().first(), isLoading = false)

            logger.debug { "\"[${Thread.currentThread().name}] [ ${this.coroutineContext[CoroutineName]} ] -> Notas cargadas" }
        }
    }

    fun onFilterAction(filter: Filter) {
        logger.debug {
            "onFilterAction ${
                when (filter) {
                    Filter.All -> "ALL"
                    is Filter.ByType -> "ByType ${filter.type}"
                }
            } "
        }
        // Actualizamos el estado
        state = state.copy(filter = filter)
    }

    // El estado de la vista
    data class UiState(
        val isLoading: Boolean = false,
        val notes: List<Note>? = null,
        val filter: Filter = Filter.All
    ) {
        val filterNotes: List<Note>?
            get() = when (filter) {
                Filter.All -> notes
                is Filter.ByType -> notes?.filter { it.type == filter.type }
            }
    }
}