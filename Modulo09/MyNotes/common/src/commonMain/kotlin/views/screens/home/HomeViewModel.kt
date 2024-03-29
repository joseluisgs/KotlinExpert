package views.screens.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import com.github.michaelbull.result.mapBoth
import errors.NoteError
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import models.Filter
import models.Note
import org.lighthousegames.logging.logging
import repository.NotesRepository

private val logger = logging()

// Extendemos de ScreenModel para poder usarlo en la navegación y
// resista a los cambios de configuración, rotaciones, y recomposiciones
// ademas ya tiene su propio scope para las corrutinas
class HomeViewModel : ScreenModel {
    // Vamos a usar MutableState de Compose
    var state by mutableStateOf(UiState())
        private set

    init {
        logger.info { "Init HomeViewModel" }

        // Nada más cargar el viewModel, cargamos las notas
        // Cargo las notas
        loadNotes()
    }

    fun loadNotes() {
        logger.debug { "Cargando notas" }
        coroutineScope.launch(CoroutineName("Corrutina loadNotes ")) {
            state = UiState(isLoading = true)

            // Creo las notas
            /*NotesRepository.getAll().collect {
                // Actualizamos la interfaz y estado
                state = state.copy(notes = it, isLoading = false)
            }*/

            // Otra forma de hacerlo
            //state = state.copy(notes = NotesRepository.getAll().first(), isLoading = false)

            // Con Result
            NotesRepository.getAll().mapBoth(
                success = {
                    state = state.copy(notes = it.first(), isLoading = false, error = null)
                },
                failure = {
                    state = state.copy(error = it, isLoading = false)
                }
            )
        }
    }

    fun onFilterAction(filter: Filter) {
        logger.debug { "Filtrando notas" }
        // Actualizamos el estado
        state = state.copy(filter = filter)
    }

    fun retryAction() {
        logger.debug { "Reintentando cargar las notas" }
        // Actualizamos el estado
        loadNotes()
    }

    // El estado de la vista
    data class UiState(
        val isLoading: Boolean = false,
        val notes: List<Note>? = null,
        val filter: Filter = Filter.All,
        val error: NoteError? = null
    ) {
        val filterNotes: List<Note>?
            get() = when (filter) {
                Filter.All -> notes
                is Filter.ByType -> notes?.filter { it.type == filter.type }
            }
    }
}