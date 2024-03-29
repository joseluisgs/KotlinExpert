package views.screens.detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import kotlinx.coroutines.launch
import models.Note
import org.lighthousegames.logging.logging
import repository.NotesRepository

// View Model de la vista Detail

private val logger = logging()

// Extendemos de ScreenModel para poder usarlo en la navegación y
// resista a los cambios de configuración, rotaciones, y recomposiciones
// ademas ya tiene su propio scope para las corrutinas
class DetailViewModel(private val idNote: Long) : ScreenModel {
    var state by mutableStateOf(UiState())
        private set

    // Si la nota es nueva, no la cargamos
    init {
        logger.info { "Init DetailViewModel con nota con id: $idNote" }

        if (idNote != Note.NEW_NOTE) {
            loadNote()
        }
    }

    // Carga una nota del servicio
    private fun loadNote() {
        logger.debug { "Cargando nota con id: $idNote" }
        coroutineScope.launch {
            state = UiState(isLoading = true)
            val note = NotesRepository.getById(idNote)
            state = UiState(note = note, isLoading = false)
        }
    }

    // Guarda una nota en el servicio, o la actualiza
    fun save() {
        logger.debug { "Guardando nota con id: ${state.note.id}" }
        coroutineScope.launch {
            var note = state.note
            state = state.copy(isLoading = true)
            // salva o actualiza según el id
            note = if (note.id == Note.NEW_NOTE) {
                NotesRepository.save(note)
            } else {
                NotesRepository.update(note)
            }
            state = state.copy(note = note, saved = true, isLoading = false)
        }
    }

    // indica que la nota se ha actualizado para recogerlo en la vista
    fun update(note: Note) {
        logger.debug { "Actualizando nota con id: ${note.id}" }
        state = state.copy(note = note)
    }

    // Borra una nota del servicio
    fun delete() {
        logger.debug { "Borrando nota con id: ${state.note.id}" }
        coroutineScope.launch {
            state = state.copy(isLoading = true)
            NotesRepository.delete(state.note.id)
            state = state.copy(saved = true, isLoading = false)
        }
    }


    // El estado de la vista, con la nota, si se está cargando y si se ha salvado
    data class UiState(
        val note: Note = Note(description = "", title = "", type = Note.Type.TEXT),
        val isLoading: Boolean = false, // indica si se está cargando la nota
        val saved: Boolean = false, // indica si la nota se ha guardado para recogerlo en la vista
    )
}