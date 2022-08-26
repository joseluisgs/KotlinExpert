import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/*
StateFlow es un Flow muy particular, porque:

Maneja un único valor en su campo value
Cada vez que se modifica, los recolectores asociados a él reciben una actualización
Nada más suscribirse, recibe el último valor asignado a value
Existen 2 variantes, StateFlow, que es inmutable (no se puede modificar value, y MutableStateFlow
En general, StateFlow sirve para almacenar un estado, y que los cambios en ese estado puedan ser escuchados de forma reactiva.

¿Te suena esto de algo? Es exactamente la definición de LiveData, pero aplicado a los Flows.
 */

data class Note(
    val title: String,
    val description: String,
    val type: Type = Type.TEXT,
) {
    enum class Type(val id: Int, val typeNote: String) {
        TEXT(1, "text"),
        AUDIO(2, "audio"),
    }
}

// Vamos a hacerlo reactivo
class ViewModel {
    // Para que no se pueda mutar desde fuera
    private var _state: MutableStateFlow<Note> =
        MutableStateFlow(Note(title = "Title 1", description = "Description 1", type = Note.Type.TEXT))

    // Creamos una bakcing properties https://kotlinlang.org/docs/properties.html#late-initialized-properties-and-variables
    val state: StateFlow<Note> = _state

    suspend fun update() {
        var count = 1
        while (true) {
            delay(2000)
            count++
            _state.value = state.value.copy(title = "Title $count", description = "Description $count")
        }
    }
}

fun main(): Unit = runBlocking {
    val viewModel = ViewModel()
    launch {
        viewModel.update()

    }
    viewModel.state.collect(::println)
}