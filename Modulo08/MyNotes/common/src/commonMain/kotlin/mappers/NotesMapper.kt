package mappers

import database.NoteEntity
import kotlinx.datetime.Instant
import models.Note

fun NoteEntity.toNote() = Note(
    id = id,
    title = title,
    description = description,
    type = Note.Type.valueOf(type),
    createdAt = Instant.parse(created_at),
)