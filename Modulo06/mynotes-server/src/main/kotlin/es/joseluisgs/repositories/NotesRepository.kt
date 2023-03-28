package es.joseluisgs.repositories

import es.joseluisgs.database.DataBaseManager
import es.joseluisgs.mappers.toModel
import es.joseluisgs.models.Note

object NotesRepository {
    private var notes = mutableListOf<Note>()
    private var currentId = 1L

    private val notesDb = DataBaseManager.notes

    /*init {
        notes = (1..10).map {
            Note(
                id = currentId++,
                title = "Title $it",
                description = "Description $it",
                type = if (it % 3 == 0) Note.Type.AUDIO else Note.Type.TEXT,
                createdAt = Clock.System.now() - (1..10000).random().toLong().seconds
            )
        }.toMutableList()
    }*/

    fun getAllNotes(): List<Note> = notesDb.selectAll().executeAsList().map { it.toModel() }

    fun getById(id: Long) = notesDb.selectById(id).executeAsOneOrNull()?.toModel()

    fun save(note: Note): Note {
        // Al usar el LastInserted lo hacemos dentro de una transacción con resultado
        return notesDb.transactionWithResult {
            notesDb.insert(
                title = note.title,
                description = note.description,
                type = note.type.name,
                created_at = note.createdAt.toString()
            )
            return@transactionWithResult notesDb.selectLastInserted().executeAsOne().toModel()
        }
    }

    fun update(note: Note): Note? {
        val updated = getById(note.id) ?: return null

        notesDb.update(
            title = note.title,
            description = note.description,
            type = note.type.name,
            id = note.id
        )

        return updated.copy(
            title = note.title,
            description = note.description,
            type = note.type
        )
    }

    fun delete(id: Long): Boolean {
        if (getById(id) == null) return false
        notesDb.delete(id)
        return true
    }
}