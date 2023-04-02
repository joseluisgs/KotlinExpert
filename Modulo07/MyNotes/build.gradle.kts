import org.jetbrains.compose.compose
import org.jetbrains.compose.desktop.application.dsl.TargetFormat

val ktor_version: String by project
val datetime_version: String by project
val logging_version: String by project
val logback_version: String by project
val coroutines_version: String by project
val sqldelight_version: String by project


group = "dev.joseluisgs"
version = "1.0-SNAPSHOT"

allprojects {
    repositories {
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}

// Inidicamos que es multiplataforma y compose
plugins {
    kotlin("multiplatform") apply false
    id("org.jetbrains.compose") apply false
    kotlin("plugin.serialization") apply false
    id("app.cash.sqldelight") apply false
}

