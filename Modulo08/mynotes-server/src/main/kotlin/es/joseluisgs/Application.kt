package es.joseluisgs

import es.joseluisgs.plugins.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import mu.KotlinLogging

private val logger = KotlinLogging.logger {}

fun main() {
    embeddedServer(
        Netty, port = 8080,
        host = "0.0.0.0",
        module = Application::module
    ).start(wait = true)
}

// Configuración de la aplicación
fun Application.module() {
    configureKoin() // Koin, el primero para que esté disponible para los demás
    configureRouting()
    configureSerialization()
    configureDataBase()
    configureCors()
}
