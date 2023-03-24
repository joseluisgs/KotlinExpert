package joseluisgs.es.repositories

import joseluisgs.es.models.Note
import kotlinx.datetime.Clock
import kotlin.time.Duration.Companion.seconds

object NotesRepository {
    private var notes = mutableListOf<Note>()
    private var currentId = 0L

    init {
        notes = (1..10).map {
            Note(
                id = ++currentId,
                title = "Title $it",
                description = "Description $it",
                type = if (it % 3 == 0) Note.Type.AUDIO else Note.Type.TEXT,
                createdAt = Clock.System.now() - (1..10000).random().toLong().seconds
            )
        }.toMutableList()
    }

    fun getAllNotes(): List<Note> = notes

    fun getById(id: Long) = notes.find { it.id == id }

    fun save(note: Note) = note.copy(id = ++currentId)
        .also(notes::add)

    fun update(note: Note) = notes.indexOfFirst { it.id == note.id }
        .takeIf { it > 0 }
        ?.also { notes[it] = note }
        ?.let { notes[it] }

    fun delete(id: Long) = notes.indexOfFirst { it.id == id }
        .takeIf { it > 0 }
        ?.also { notes.removeAt(it) }
        .let { it != null }
}