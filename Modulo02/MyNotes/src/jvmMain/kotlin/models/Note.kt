package models

// data class Note, ideal como POKO de una nota para nuestra app
// Type será su clase internal
data class Note(val title: String, val description: String, val type: Type = Type.TEXT) {
    // Le voy a meter enums con campos extras para identificarlas mejor
    enum class Type(val id: Int, val typeNote: String) {
        TEXT(1, "text"),
        AUDIO(2, "audio"),
    }
}

// Coleccion de notas, no hay que ponerlas todas los tipos porque está por defecto
val notes = listOf<Note>(
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
)

