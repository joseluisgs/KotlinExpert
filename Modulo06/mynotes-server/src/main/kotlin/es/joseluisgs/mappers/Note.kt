package es.joseluisgs.mappers

import es.joseluisgs.models.Note
import esjoseluisgs.database.NoteEntity
import kotlinx.datetime.Instant

fun Note.toEntity() = NoteEntity(
    id = this.id,
    title = this.title,
    description = this.description,
    type = this.type.toString(),
    created_at = this.createdAt.toString(),
)

fun NoteEntity.toModel() = Note(
    id = this.id,
    title = this.title,
    description = this.description,
    type = Note.Type.valueOf(this.type),
    createdAt = Instant.parse(this.created_at)
)