package joseluisgs.es.models

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable
import kotlin.time.Duration.Companion.seconds

@Serializable
data class Note(
    val title: String,
    val description: String,
    val type: Type,
) {
    // https://github.com/Kotlin/kotlinx-datetime Para experimentar con fechas
    val createdAt: Instant = Clock.System.now() - (1..10000).random().toLong().seconds

    // Le voy a meter enums con campos extras para identificarlas mejor
    enum class Type { TEXT, AUDIO }
}

