import kotlinx.coroutines.*

// Una de las maneras de no poner runblocking en Main!!!

suspend fun main(){
    callSuspendFun() // No se imprime en orden...porqu euno tarda mas en ejecutarse que el otro

    val result1 = waitFor()

    println("Result1: $result1")

    // Si queremos asyncrono
    val myScope = CoroutineScope(Dispatchers.Default)
    val result2 = myScope.async { waitFor() }
    println("Result2: ${result2.await()}")
}

// Función de suspensión paraue hace el trabajo en background!!!
suspend fun doWorkAndReturnDouble(num: Int): Int {
    delay(1000)
    return num * 2
}


// Así hacemos la llamada
suspend fun callSuspendFun() {
    // Siempre hay que decirle el scope principal... Por ejmplo si estamos en distointos ambitos
    val coroutineScope = CoroutineScope(Dispatchers.Default)
    // Lanzamos nuestros hilos sobre main
    val job01 = coroutineScope.launch {
        var num: Int? = null
        // Ahora decimos que lo cambiamos a una IO
        withContext(Dispatchers.IO) {
            val newNum = 10
            num = doWorkAndReturnDouble(newNum)
        }
        println(num)
    }
}

suspend fun waitFor(): String = withContext(Dispatchers.IO) {
    delay(1000)
    "Hola"
}

