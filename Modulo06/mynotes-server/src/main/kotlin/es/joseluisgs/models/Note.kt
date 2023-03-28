package es.joseluisgs.models

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class Note(
    val id: Long = -1,
    val title: String,
    val description: String,
    val type: Type,
    val createdAt: Instant = Clock.System.now(),
) {


    // Le voy a meter enums con campos extras para identificarlas mejor
    enum class Type { TEXT, AUDIO }
}

