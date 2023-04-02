package es.joseluisgs.database

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import es.joseluisgs.entities.NotesTable
import es.joseluisgs.utils.ConfigProperties
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

    private val DATABASE_URL by lazy { ConfigProperties.getProperty("database.url") }
    private val DATABASE_NAME by lazy { ConfigProperties.getProperty("database.name") }
    private val DATABASE_DRIVER by lazy { ConfigProperties.getProperty("database.driver") }
    private val DATABASE_USER by lazy { ConfigProperties.getProperty("database.username") }
    private val DATABASE_PASSWORD by lazy { ConfigProperties.getProperty("database.password") }

    private val APP_NAME: String by lazy { ConfigProperties.getProperty("app.name") }
    private val APP_TITLE: String by lazy { ConfigProperties.getProperty("app.title") }
    private val NOTES_API_URL: String by lazy { ConfigProperties.getProperty("rest.api.url") }

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