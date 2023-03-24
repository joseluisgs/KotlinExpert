package joseluisgs.es.models

import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class Note(
    val id: Long,
    val title: String,
    val description: String,
    val type: Type,
    val createdAt: Instant
) {
    // Le voy a meter enums con campos extras para identificarlas mejor
    enum class Type { TEXT, AUDIO }
}

