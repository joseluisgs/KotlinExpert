package joseluisgs.es.repositories

import joseluisgs.es.models.Note
import kotlinx.datetime.Clock
import kotlin.time.Duration.Companion.seconds

object NotesRepository {
    private var notes = mutableListOf<Note>()

    init {
        notes = (1..10).map {
            Note(
                title = "Title $it",
                description = "Description $it",
                type = if (it % 3 == 0) Note.Type.AUDIO else Note.Type.TEXT,
                createdAt = Clock.System.now() - (1..10000).random().toLong().seconds
            )
        }.toMutableList()
    }

    fun getAllNotes(): List<Note> {
        return notes
    }
}