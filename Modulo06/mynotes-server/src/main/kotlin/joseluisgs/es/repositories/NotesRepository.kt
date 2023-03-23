package joseluisgs.es.repositories

import joseluisgs.es.models.Note

object NotesRepository {

    fun getAllNotes(): List<Note> {
        return (1..10).map {
            Note(
                title = "Title $it",
                description = "Description $it",
                type = if (it % 3 == 0) Note.Type.AUDIO else Note.Type.TEXT
            )
        }
    }
}