package es.joseluisgs.database

import es.joseluisgs.entities.NotesTable
import mu.KotlinLogging
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.core.annotation.Single


private val logger = KotlinLogging.logger {}

// Lo mejor es sacarlo a un fichero de configuración
private const val DATABASE_URL = "jdbc:h2:file:./"
private const val DATABASE_NAME = "Notes"
private const val DATABASE_DRIVER = "org.h2.Driver"
private const val DATABASE_USER = "sa"
private const val DATABASE_PASSWORD = ""

@Single
class DataBaseManager {

    init {
        logger.debug { "Conectando la base de datos" }
        Database.connect(
            url = "$DATABASE_URL$DATABASE_NAME;DB_CLOSE_DELAY=-1",
            driver = DATABASE_DRIVER,
            user = DATABASE_USER,
            password = DATABASE_PASSWORD
        )
    }

    fun initDataBase() = transaction {
        logger.debug { "Inicializando la base de datos" }
        SchemaUtils.create(NotesTable)
    }
}