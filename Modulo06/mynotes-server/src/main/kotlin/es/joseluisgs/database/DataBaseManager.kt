package es.joseluisgs.database

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import java.io.File

private const val DATABASE_NAME = "notes.db"

object DataBaseManager {
    // Podemos usar un driver de memoria o lo que queramos
    // private val driver: SqlDriver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
    private val driver: SqlDriver = JdbcSqliteDriver("jdbc:sqlite:$DATABASE_NAME")

    init {
        // Creamos la base de datos si el fichero no existe
        if (!File(DATABASE_NAME).exists()) {
            AppDatabase.Schema.create(driver)
        }

        // queremos limpiar la base de datos
        // removeAllData()
    }

    val notes = AppDatabase(driver).noteQueries

    // limpiamos las tablas
    fun removeAllData() {
        // Limpiamos las tablas
        notes.transaction {
            notes.removeAll()
        }
    }

}