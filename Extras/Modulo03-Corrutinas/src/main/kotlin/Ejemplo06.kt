import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
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
    val state = _state //.receiveAsFlow() // asi se verá como un flow

    suspend fun update() {
        var count = 1
        while (!_state.isClosedForSend) {
            delay(500)
            count++
            println("Emitiendo: Title $count")
            _state.send(Note(title = "Title $count", description = "Description $count"))
            if (count > 6) {
                _state.close()
            }
        }
    }
}

fun main(): Unit = runBlocking {
    val viewModel = ViewModelChannel()
    launch {
        viewModel.update()

    }
    for (state in viewModel.state) {
        delay(1000)
        println("Recolectando: $state")
    }
//    viewModel.state.collect {
//        delay(1000)
//        println("Recolectando: $it")
//    }
}