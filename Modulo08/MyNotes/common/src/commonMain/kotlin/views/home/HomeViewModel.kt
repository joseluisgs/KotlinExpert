package views.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import models.Filter
import models.Note
import mu.KotlinLogging
import repository.NotesRepository


private val logger = KotlinLogging.logger {}

class HomeViewModel(private val scope: CoroutineScope) {
    // Vamos a usar MutableState de Compose
    var state by mutableStateOf(UiState())
        private set

    init {
        // Nada más cargar el viewModel, cargamos las notas
        logger.debug { "HomeViewModel.init()" }
        // Cargo las notas
        loadNotes()
    }


    private fun loadNotes() {
        scope.launch(CoroutineName("Corrutina loadNotes ")) {
            logger.debug { "[ ${this.coroutineContext[CoroutineName]} ] -> Cargando notas" }
            state = UiState(isLoading = true)

            // Creo las notas
            logger.debug { "[ ${this.coroutineContext[CoroutineName]} ] -> Consumiendo las notas" }
            NotesRepository.getAll().collect {
                // Actualizamos la interfaz y estado
                logger.debug { " [ ${this.coroutineContext[CoroutineName]} ] -> Consumiendo El flow de notas" }
                state = state.copy(notes = it, isLoading = false)
            }

            // Otra forma de hacerlo
            //state = state.copy(notes = NotesRepository.getAll().first(), isLoading = false)

            logger.debug { "[ ${this.coroutineContext[CoroutineName]} ] -> Notas cargadas" }
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
        val filter: Filter = Filter.All,
    ) {
        val filterNotes: List<Note>?
            get() = when (filter) {
                Filter.All -> notes
                is Filter.ByType -> notes?.filter { it.type == filter.type }
            }
    }
}