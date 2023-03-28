package es.joseluisgs.repositories

import es.joseluisgs.entities.NotesDao
import es.joseluisgs.mappers.toNote
import es.joseluisgs.models.Note
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import mu.KotlinLogging
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.experimental.suspendedTransactionAsync
import org.koin.core.annotation.Single

private val logger = KotlinLogging.logger {}

@Single
class NotesRepository {

    suspend fun getAllNotes(): Flow<Note> = newSuspendedTransaction(Dispatchers.IO) {
        logger.debug { "findAll()" }
        NotesDao.all().map { it.toNote() }.asFlow()
    }

    suspend fun getById(id: Long): Deferred<Note?> = suspendedTransactionAsync(Dispatchers.IO) {
        logger.debug { "findById($id)" }
        NotesDao.findById(id)?.toNote()
    }

    suspend fun save(note: Note): Deferred<Note> = suspendedTransactionAsync(Dispatchers.IO) {
        logger.debug { "save($note)" }
        return@suspendedTransactionAsync NotesDao.new {
            title = note.title
            description = note.description
            type = note.type.name
            createdAt = Clock.System.now().toLocalDateTime(TimeZone.UTC)
        }.toNote()
    }

    suspend fun update(note: Note): Deferred<Note?> = suspendedTransactionAsync(Dispatchers.IO) {
        logger.debug { "update($note)" }
        val updated = NotesDao.findById(note.id) ?: return@suspendedTransactionAsync null

        return@suspendedTransactionAsync updated.apply {
            title = note.title
            description = note.description
            type = note.type.name
        }.toNote()

    }

    suspend fun delete(id: Long): Deferred<Boolean> = suspendedTransactionAsync(Dispatchers.IO) {
        logger.debug { "delete($id)" }
        val deleted = NotesDao.findById(id) ?: return@suspendedTransactionAsync false
        deleted.delete()
        return@suspendedTransactionAsync true
    }
}