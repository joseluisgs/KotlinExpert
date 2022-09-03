import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/*
Los Canales nos sirven para compartir información entre corrutinas, de manera que es como una cola bloqueante,
por lo que una vez se recolecta un valor, este deja de estar disponible para otros consumidores.
https://kotlinlang.org/docs/channels.html
Hasta que no se trata un valor enviado no se puede enviar el siguiente
Lo podemos cambiar con Capacity
*/

class ViewModelChannel {
    private var _state = Channel<Note>(capacity = 3)
    val state = _state.receiveAsFlow() // asi se verá como un flow

    suspend fun update() {
        var count = 1
        while (true) {
            delay(500)
            count++
            println("Emitiendo: Title $count")
            _state.send(Note(title = "Title $count", description = "Description $count"))
        }
    }
}

fun main(): Unit = runBlocking {
    val viewModel = ViewModelChannel()
    launch {
        viewModel.update()

    }
    viewModel.state.collect {
        delay(1000)
        println("Recolectando: $it")
    }
}