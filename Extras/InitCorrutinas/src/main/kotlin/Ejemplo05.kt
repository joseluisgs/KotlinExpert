import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/*
sharedFlow
Es el caso genérico del StateFlow, pudiéndolo configurar como queramos
en los parámetros de entrada.
Con el número máximo de componentes, tiempo máximo, etc

https://kotlinlang.org/api/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-shared-flow/
https://medium.com/kotlin-en-android/flujos-de-datos-con-sharedflow-e765acc506da
 */

// Vamos a hacerlo reactivo
class ViewModel2 {
    // Para que no se pueda mutar desde fuera
    private var _state = MutableSharedFlow<Note>()

    // Creamos una bakcing properties https://kotlinlang.org/docs/properties.html#late-initialized-properties-and-variables
    val state = _state.asSharedFlow()

    suspend fun update() {
        var count = 1
        while (true) {
            delay(500)
            _state.emit(Note(title = "Title $count", description = "Description $count"))
            count++
        }
    }
}

fun main(): Unit = runBlocking {
    val viewModel = ViewModel2()
    launch {
        viewModel.update()

    }
    viewModel.state.collect(::println)
}