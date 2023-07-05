package config

import utils.ConfigProperties

// Definimos un objeto que nos permita acceder a las propiedades de configuración forma sencilla
object AppConfig {
    val APP_NAME: String by lazy { ConfigProperties.getProperty("app.name") }
    val APP_TITLE: String by lazy { ConfigProperties.getProperty("app.title") }
    val NOTES_API_URL: String by lazy { ConfigProperties.getProperty("rest.api.url") }
}