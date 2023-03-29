package es.joseluisgs.plugins

import es.joseluisgs.database.DataBaseManager
import io.ktor.server.application.*
import org.koin.ktor.ext.inject

fun Application.configureDataBase() {
    val dataBaseManager: DataBaseManager by inject()
    dataBaseManager.initDataBase()
}