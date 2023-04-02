package data.database

import app.cash.sqldelight.db.SqlDriver
import database.NotesQueries

// Como puede cambiar en cada plataforma, se usa expect para declarar la función en donde corresponda
expect fun provideDbDriver(): SqlDriver

expect fun provideNotesQueries(): NotesQueries