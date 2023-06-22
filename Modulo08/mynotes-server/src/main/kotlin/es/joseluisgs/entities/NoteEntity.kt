package es.joseluisgs.entities

import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.kotlin.datetime.datetime


// Tabla de Notas
object NotesTable : LongIdTable("NOTES") {
    val title = varchar("title", 100)
    val description = varchar("description", 1000)
    val type = varchar("type", 20)
    val createdAt = datetime("created_at")
}

// DAO de RaquetasTable
class NotesDao(id: EntityID<Long>) : LongEntity(id) {
    // mi id será el de la tabla...
    companion object : LongEntityClass<NotesDao>(NotesTable)

    var title by NotesTable.title
    var description by NotesTable.description
    var type by NotesTable.type
    var createdAt by NotesTable.createdAt
}