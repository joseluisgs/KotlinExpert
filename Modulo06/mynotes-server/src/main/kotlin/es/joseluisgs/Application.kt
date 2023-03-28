package es.joseluisgs

import es.joseluisgs.plugins.configureRouting
import es.joseluisgs.plugins.configureSerialization
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

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
