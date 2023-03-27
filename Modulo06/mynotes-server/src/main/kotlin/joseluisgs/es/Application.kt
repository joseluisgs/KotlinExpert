package joseluisgs.es

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import joseluisgs.es.plugins.configureRouting
import joseluisgs.es.plugins.configureSerialization

fun main() {
    embeddedServer(
        Netty, port = 8080,
        host = "0.0.0.0",
        module = Application::module
    ).start(wait = true)
}

// Configuración de la aplicación
fun Application.module() {
    configureRouting()
    configureSerialization()
}
