package views.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import models.Filter
import models.Note
import repository.NotesRepository


class HomeViewModel(private val scope: CoroutineScope) {
    // Vamos a usar MutableState de Compose
    var state by mutableStateOf(UiState())
        private set

    init {
        // Nada más cargar el viewModel, cargamos las notas
        // Cargo las notas
        loadNotes()
    }


    private fun loadNotes() {
        scope.launch(CoroutineName("Corrutina loadNotes ")) {
            state = UiState(isLoading = true)

            // Creo las notas
            NotesRepository.getAll().collect {
                // Actualizamos la interfaz y estado
                state = state.copy(notes = it, isLoading = false)
            }

            // Otra forma de hacerlo
            //state = state.copy(notes = NotesRepository.getAll().first(), isLoading = false)

        }
    }

    fun onFilterAction(filter: Filter) {
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