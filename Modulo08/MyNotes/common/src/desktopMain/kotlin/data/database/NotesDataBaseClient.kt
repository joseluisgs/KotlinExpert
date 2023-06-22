package data.database

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import database.AppDatabase
import database.NotesQueries

actual fun provideDbDriver(): SqlDriver {
    // val driver: SqlDriver = JdbcSqliteDriver("jdbc:sqlite:./notes.db")
    return JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY).also {
        AppDatabase.Schema.create(it)
    }
}

actual fun provideNotesQueries(): NotesQueries {
    // Return the queries
    return AppDatabase(provideDbDriver()).notesQueries
}