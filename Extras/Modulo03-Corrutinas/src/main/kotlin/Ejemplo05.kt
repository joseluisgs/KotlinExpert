import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/*
sharedFlow
Es el caso genérico del StateFlow, pudiéndolo configurar como queramos
en los parámetros de entrada.
Con el número máximo de componentes, tiempo máximo, etc

replay, cuantos elementos almacena para cada vez que se suscriba alguien los re-emita
extraBufferCapacity: Si recolectamos valores más lento de los que se están emitiendo, si no tenemos un buffer se bloquea
hasta que no se consuma el replay
Si queremos que no suspenda o que descarte con onBufferOverflow, DROP_OLDEST descarta los que no se han podido consumir
DROP_LATEST descarta los nuevos

https://kotlinlang.org/api/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-shared-flow/
https://medium.com/kotlin-en-android/flujos-de-datos-con-sharedflow-e765acc506da
 */

// Vamos a hacerlo reactivo
class ViewModel2 {
    // Para que no se pueda mutar desde fuera
    /*private var _state =
        MutableSharedFlow<Note>(replay = 3, extraBufferCapacity = 3, onBufferOverflow = BufferOverflow.DROP_OLDEST)*/

    // Si queremos que se comporte como un stateFlow
    private var _state =
        MutableSharedFlow<Note>(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

    // Creamos una bakcing properties https://kotlinlang.org/docs/properties.html#late-initialized-properties-and-variables
    val state = _state.asSharedFlow()

    suspend fun update() {
        var count = 1
        while (true) {
            delay(500)
            _state.emit(Note(title = "Title 1", description = "Description 1"))
            println("Emitiendo: Title 1")
            count++
        }
    }
}

fun main(): Unit = runBlocking {
    val viewModel = ViewModel2()
    launch {
        viewModel.update()

    }
    delay(2100) // Si me espero, pues perdemos datos, el primero!!!
    // si son iguales no debería consumir repetidos!!!
    viewModel.state.distinctUntilChanged().collect() {
        delay(1000)
        println("Recolectando: $it")
    }
}