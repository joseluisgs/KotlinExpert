package views.detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import models.Note
import mu.KotlinLogging
import repository.NotesRepository

private val logger = KotlinLogging.logger {}

class DetailViewModel(private val scope: CoroutineScope, private val idNote: Long) {
    var state by mutableStateOf(UiState())
        private set

    // Si la nota es nueva, no la cargamos
    init {
        if (idNote != Note.NEW_NOTE) {
            loadNote()
        }
    }

    // Carga una nota del servicio
    private fun loadNote() {
        logger.debug { "load Note" }
        scope.launch {
            state = UiState(isLoading = true)
            val note = NotesRepository.getById(idNote)
            state = UiState(note = note, isLoading = false)
        }
    }

    // Guarda una nota en el servicio, o la actualiza
    fun save() {
        logger.debug { "save Note" }
        scope.launch {
            var note = state.note
            state = state.copy(isLoading = true)
            note = if (note.id == Note.NEW_NOTE) {
                NotesRepository.save(note)
            } else {
                NotesRepository.update(note)
            }
            state = state.copy(note = note, saved = true, isLoading = false)
        }
    }

    // indica que la nota se ha guardado para recogerlo en la vista
    fun update(note: Note) {
        logger.debug { "update Note" }
        state = state.copy(note = note)
    }

    fun delete() {
        logger.debug { "delete Note" }
        scope.launch {
            state = state.copy(isLoading = true)
            NotesRepository.delete(state.note.id)
            state = state.copy(saved = true, isLoading = false)
        }
    }


    // El estado de la vista
    data class UiState(
        val note: Note = Note(description = "", title = "", type = Note.Type.TEXT),
        val isLoading: Boolean = false, // indica si se está cargando la nota
        val saved: Boolean = false, // indica si la nota se ha guardado para recogerlo en la vista
    )
}
