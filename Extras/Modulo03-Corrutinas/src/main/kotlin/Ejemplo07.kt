import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/*
CallbackFlow:
Permite tratar funciones que trabajan con callback en flujos
https://kotlinlang.org/api/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/callback-flow.html
https://devexperto.com/callbackflow-kotlin/
 */

class ViewModelCallback {

    fun update(callback: (Note) -> Unit) {
        var count = 1
        while (true) {
            Thread.sleep(500)
            println("Emitiendo: Title $count")
            callback(Note(title = "Title $count", description = "Description $count"))
            count++
        }
    }
}

// Imaginamos que no podemos acceder al ViewModelCallback porque es de un framework o librería externa
// Podemos usar una función de extensión

fun ViewModelCallback.udpdatesFlow(): Flow<Note> = callbackFlow {
    update { trySend(it) }
}

fun main(): Unit = runBlocking {
    val viewModel = ViewModelCallback()
    launch(Dispatchers.Default) {
        delay(1000)
        viewModel.udpdatesFlow().collect {
            println("Recolectando: $it")
        }
    }
}