import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

suspend fun main() {

    println("Secuencial")
    secencuial()
    println("Lunch01")
    conLunch01()
    println("Lunch02")
    conLunch02()
    println("Async")
    conAsync()
}

// Con WithContext decimos que dispacher o grupos d ehilos van a responsabilizarse...
// Si no, lo hará por defecrto de quien la llame...
suspend fun doSomethingUsefulOne(): Int = withContext(Dispatchers.IO) {
    // Imaginemos un login o acceso a base de datos
    log("doSomethingUsefulOne")
    delay(1000L) // pretend we are doing something useful here
    13
}

suspend fun doSomethingUsefulTwo(): Int = withContext(Dispatchers.Default) {
    // Imaginemos una consulta a api rest
    log("doSomethingUsefulTwo")
    delay(1000L) // pretend we are doing something useful here, too
    29
}

/**
 * Por qué ponerlecoroutien scope, hereda el scope del superior si falla alguno se propaga el fallo f
 */

// Podemos añadirle si queremos el scope el coruitineScope, si queremos
suspend fun secencuial() = coroutineScope { // Estoes opcional pero es una buena prática...
    log("Secuencial")
    val time = measureTimeMillis {
        val one = doSomethingUsefulOne()
        val two = doSomethingUsefulTwo()
        println("The answer secuencial is ${one + two}")
    }
    log("Completed secuencial in $time ms")
}

suspend fun conLunch01() { // Aqui no se lo he puesto y no lo hereda...
    log("con Lunch01")
    val time = measureTimeMillis {
        val myScope = CoroutineScope(Dispatchers.Default)
        var res1 = 0
        var res2 = 0
        // Lunch lanza en cada conjunto de hilos las acciones, estas van secuenciales...
        val one = myScope.launch {
            res1= doSomethingUsefulOne()
            res2= doSomethingUsefulTwo()}
        // Com puede ver no termina, por lo que ddebemos obligar nosotros
        // a esperar a que terminen las dos coroutines.
        one.join()
        println("The answer con Lunch01 is ${res1 + res2}")
    }

    log("Completed con lunch01 in $time ms")
}

suspend fun conLunch02() = coroutineScope {
    log("con Lunch02")
    val time = measureTimeMillis {
        val myScope = CoroutineScope(Dispatchers.Default)
        var res1 = 0
        var res2 = 0
        // Lunch lanza en cada conjunto de hilos las acciones...salen en paralelo
        // Lunch no devuleve el resultado, por eso debemos hacer esta "chapuza!!!
        val one = myScope.launch { res1= doSomethingUsefulOne() }
        val two = myScope.launch { res2= doSomethingUsefulTwo() }
        // Com puede ver no termina, por lo que ddebemos obligar nosotros
        // a esperar a que terminen las dos coroutines.
        one.join()
        two.join()
        println("The answer con Lunch02 is ${res1 + res2}")
    }

    log("Completed con lunch02 in $time ms")
}

suspend fun conAsync() = coroutineScope {
    log("con Async")
    val time = measureTimeMillis {
        val myScope = CoroutineScope(Dispatchers.Default)
        // async lanza en paralelo, pero devuleve un resultado...
        val one = myScope.async { doSomethingUsefulOne() }
        val two = myScope.async { doSomethingUsefulTwo() }

        println("The answer con Async is ${one.await() + one.await()}")
    }

    log("Completed con Async in $time ms")
}