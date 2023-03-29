package data.api

import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

// Creamos un cliente REST para poder hacer las llamadas a la API con Ktor
val notesRestClient = HttpClient(OkHttp) {
    // Configuración
    install(ContentNegotiation) {
        // Configuramos el cliente para que trabaje con JSON
        json(Json {
            prettyPrint = true
            isLenient = true
        })
    }
}