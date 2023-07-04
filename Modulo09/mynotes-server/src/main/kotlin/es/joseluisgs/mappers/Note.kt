package es.joseluisgs.mappers

import es.joseluisgs.entities.NotesDao
import es.joseluisgs.models.Note
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant

fun NotesDao.toNote() = Note(
    id = this.id.value,
    title = this.title,
    description = this.description,
    type = Note.Type.valueOf(this.type),
    createdAt = this.createdAt.toInstant(TimeZone.UTC) // UTC para ser universal!!
)
