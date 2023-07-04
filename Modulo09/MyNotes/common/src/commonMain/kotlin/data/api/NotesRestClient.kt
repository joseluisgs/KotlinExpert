package data.api

import io.ktor.client.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*

// Creamos un cliente REST para poder hacer las llamadas a la API con Ktor
fun provideNotesRestClient() = HttpClient {
    // Configuración
    install(ContentNegotiation) {
        // Configuramos el cliente para que trabaje con JSON
        json()
    }
}