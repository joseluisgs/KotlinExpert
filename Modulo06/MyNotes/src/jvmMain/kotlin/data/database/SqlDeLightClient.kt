package data.database

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import database.AppDatabase
import mu.KotlinLogging

private val logger = KotlinLogging.logger {}

object SqlDeLightClient {
    // Podemos usar un driver de memoria o lo que queramos
    // En fichero
    // val driver: SqlDriver = JdbcSqliteDriver("jdbc:sqlite:./notes.db")
    val noteQueries = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY).let { driver ->
        // Creamos la base de datos
        logger.debug { "SqlDeLightClient.init() - Create Schemas" }
        AppDatabase.Schema.create(driver)
        AppDatabase(driver)
    }.notesQueries


    // limpiamos las tablas
    fun removeAllData() {
        logger.debug { "SqlDeLightClient.removeAllData()" }
        noteQueries.transaction {
            logger.debug { "SqlDeLightClient.removeAllData() - users " }
            noteQueries.removeAll()
        }
    }
}