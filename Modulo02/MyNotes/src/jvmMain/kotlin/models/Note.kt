package models

import kotlinx.datetime.*
import kotlin.time.Duration.Companion.seconds

// data class Note, ideal como POKO de una nota para nuestra app
// Type será su clase internal
data class Note(
    val title: String,
    val description: String,
    val type: Type = Type.TEXT,
) {
    // https://github.com/Kotlin/kotlinx-datetime Para experimentar con fechas
    val createdAt: Instant = Clock.System.now() - (1..10000).random().toLong().seconds

    // Le voy a meter enums con campos extras para identificarlas mejor
    enum class Type(val id: Int, val typeNote: String) {
        TEXT(1, "text"),
        AUDIO(2, "audio"),
    }

    fun getMoment(): String {
        val period: DateTimePeriod = createdAt.periodUntil(
            other = Clock.System.now(),
            timeZone = TimeZone.currentSystemDefault()
        )
        return StringBuilder().apply {
            append("Hace: ")
            if (period.years > 0) {
                append("${period.years}a, ")
            }
            if (period.months > 0) {
                append("${period.months}m, ")
            }
            if (period.days > 0) {
                append("${period.days}d, ")
            }
            if (period.hours > 0) {
                append("${period.hours}h, ")
            }
            if (period.minutes > 0) {
                append("${period.minutes}min, ")
            }
            append("${period.seconds}seg")
        }.toString()
    }
}

fun getNotes() = (1..10).map {
    Note(
        title = "Title $it",
        description = "Description $it",
        type = if (it % 3 == 0) Note.Type.AUDIO else Note.Type.TEXT
    )
}.sortedByDescending { it.createdAt }


// Coleccion de notas, no hay que ponerlas todas los tipos porque está por defecto
/*val notes = listOf<Note>(
    Note("Title 1", "Description 1", Note.Type.TEXT),
    Note("Title 2", "Description 2"),
    Note("Title 3", "Description 3"),
    Note("Title 4", "Description 4"),
    Note("Title 5", "Description 5"),
    Note("Title 6", "Description 6"),
    Note("Title 7", "Description 7"),
    Note("Title 8", "Description 8"),
    Note("Title 9", "Description 9"),
    Note("Title 10", "Description 10"),
)*/

