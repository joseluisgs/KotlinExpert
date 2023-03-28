package data.api

import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*

val notesRestClient = HttpClient(OkHttp) {
    // Configuración
    install(ContentNegotiation) {
        json()
    }
}