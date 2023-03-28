package es.joseluisgs.database

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
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

    // Configuración de HikariCP para el pool de conexiones y tener más control
    private val hikariConfig by lazy {
        HikariConfig().apply {
            jdbcUrl = "$DATABASE_URL$DATABASE_NAME;DB_CLOSE_DELAY=-1"
            driverClassName = DATABASE_DRIVER
            username = DATABASE_USER
            password = DATABASE_PASSWORD
            // maximumPoolSize = 10 // 10 conexiones por defecto
        }
    }


    init {
        logger.debug { "Conectando la base de datos" }

        Database.connect(HikariDataSource(hikariConfig))

    }

    fun initDataBase() = transaction {
        logger.debug { "Inicializando la base de datos" }
        SchemaUtils.create(NotesTable)
    }
}