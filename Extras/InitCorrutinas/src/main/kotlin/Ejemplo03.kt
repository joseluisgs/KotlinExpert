import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

// Flujos con coroutines.

fun main(): Unit = runBlocking {
    println("Inicio")

    val flow = flow {
        for (i in 1..3) {
            emit(i)
            delay(1000)
        }
        emit(4)
        delay(1000)
        emit(5)
    }.filter { it > 2 }
        .map { it * 2 }
        .transform { if (it > 4) emit("Item: $it") } // Para trasformaciones más complejas
    // Cada que te conectas a un nuevo flujo, comienza desde el principio.
    launch {
        delay(2000)
        flow.collect { value ->
            println("uno: $value")
        }
    }
    launch {
        flow.collect { value ->
            println("dos: $value")
        }
    }

    val flow1 = flowOf(1, 2, 3, 4, 5).onEach { delay(300) }
    val flow2 = flowOf("a", "b", "c", "d", "e").onEach { delay(500) }
    flow1.zip(flow2) { a, b -> "$a->$b" }.collect { println(it) } // Van a la par
    flow1.combine(flow2) { a, b -> "$a->$b" }.collect { println(it) } // Casa el que tenga
    flow1.zip(flow2) { a, b -> "$a->$b" }.toList().forEach { println("Lista:$it") } // Van a la par


    // Los flows no se pueden cambiar de contexto
    val flow3 = flow {
        //withContext(Dispatchers.IO) { // Excepción!!! si no pongo floOn
        for (i in 1..3) {
            emit(i)
            delay(1000)
        }
        throw IllegalStateException("Error Flujo")
        //}
    }

    flow3.flowOn(Dispatchers.IO)
        .catch {
            println("Error: $it")
        }
        .collect { value ->
            println("flow3: $value")
        }
}