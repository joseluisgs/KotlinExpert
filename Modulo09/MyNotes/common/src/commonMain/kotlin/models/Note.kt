package models

import kotlinx.datetime.*
import kotlinx.serialization.Serializable

// data class Note, ideal como POKO de una nota para nuestra app
//  // https://github.com/Kotlin/kotlinx-datetime Para experimentar con fechas
// Type será su clase internal
@Serializable
data class Note(
    val id: Long = NEW_NOTE,
    val title: String,
    val description: String,
    val type: Type = Type.TEXT,
    val createdAt: Instant = Clock.System.now(),
) {

    // Para la creación de una nueva nota
    companion object {
        const val NEW_NOTE = -1L
    }


    // Le voy a meter enums con campos extras para identificarlas mejor
    enum class Type(val id: Int) {
        TEXT(1),
        AUDIO(2),
    }

    // Para sacar el momento de creación de la nota
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

