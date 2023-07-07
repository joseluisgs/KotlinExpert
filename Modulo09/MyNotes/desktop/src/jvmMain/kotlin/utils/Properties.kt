package utils

import java.util.*

abstract class PropertiesReader(private val fileName: String) {
    private val properties by lazy { Properties() }

    init {
        val file = this::class.java.classLoader.getResourceAsStream(fileName)
        properties.load(file)
    }

    fun getProperty(key: String): String =
        properties.getProperty(key) ?: throw IllegalStateException("Propiedad $key no encontrada en $fileName")
}

// Definimos una por cada tipo de propiedades, puede ser singleton
object ConfigProperties : PropertiesReader("config.properties")

// class DatabaseProperties(): PropertiesReader("database.properties")

// class ServicesProperties(): PropertiesReader("services.properties")