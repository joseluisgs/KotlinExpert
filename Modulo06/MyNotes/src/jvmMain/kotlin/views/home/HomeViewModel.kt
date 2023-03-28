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

// Para el ejercicio de los delegados 4.17
//private operator fun <T> StateFlow<T>.getValue(thisRef: Any?, property: KProperty<*>): T = this.value
//private operator fun <T> MutableStateFlow<T>.setValue(thisRef: Any?, property: KProperty<*>, newValue: T) {
//    this.value = newValue
//}

// ahora le paso el scope de corrutinas, para
class HomeViewModel(private val scope: CoroutineScope) {
    // Estados privados mutables, públicos inmutables. Paso a Flow para estado compartido
    //private val _state = MutableStateFlow(UiState())
    // val state = _state.asStateFlow()

    // Vamos a usar los mutable stte de compose
    var state by mutableStateOf(UiState())
        private set

    // Dos posibilidades, o nos creamos aquñi un scope  de corrutinas para consumilar o transformarla en suspend

    init {
        // Nada más cargar el viewModel, cargamos las notas
        loadNotes()
    }

    private fun loadNotes() {
        //debemos hacerlo en un hilo aparte para no bloquear, hasta qu eveamos las corrutinas

        scope.launch(CoroutineName("Corrutina loadNotes ")) {
            logger.debug { "[${Thread.currentThread().name}] [ ${this.coroutineContext[CoroutineName]} ] -> Cargando notas" }
            state = UiState(isLoading = true)
            //state = UiState(isLoading = true)
            NotesRepository.getAll().collect {
                logger.debug { "\"[${Thread.currentThread().name}] [ ${this.coroutineContext[CoroutineName]} ] -> Consumiendo Notas" }
                // cada vez que recibimos un valor se lo sumamos a la lista original
                // Actualizamos la interfaz
                state = UiState(notes = it, isLoading = false)
                // state = UiState(notes = it, isLoading = false)
            }
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
        // Podemos hacerlo así
        //_state.value = _state.value.copy(filter = filter)
        // O usar el update del stateFlow
        // state.update { it.copy(filter = filter) }
        // O usar el delegado
        state = state.copy(filter = filter)
    }

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