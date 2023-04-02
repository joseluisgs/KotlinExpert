import org.jetbrains.compose.compose
import org.jetbrains.compose.desktop.application.dsl.TargetFormat

val ktor_version: String by project
val datetime_version: String by project
val logging_version: String by project
val logback_version: String by project
val coroutines_version: String by project
val sqldelight_version: String by project

// Inidicamos que es multiplataforma y compose
plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
    kotlin("plugin.serialization") version "1.8.10"
    id("app.cash.sqldelight")
}

group = "es.joseluisgs"
version = "1.0-SNAPSHOT"

repositories {
    google()
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
}

kotlin {
    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = "17" // Java 17 o Java 11?
        }
        withJava()
    }
    sourceSets {
        val jvmMain by getting {
            dependencies {
                implementation(compose.desktop.currentOs)

                // Para iconos de la aplicación
                implementation(compose.materialIconsExtended)
                implementation("org.jetbrains.kotlinx:kotlinx-datetime:$datetime_version")

                // Logger
                implementation("io.github.microutils:kotlin-logging:$logging_version")

                // Salida del logger
                implementation("ch.qos.logback:logback-classic:$logback_version")

                // Corrutinas
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-jvm:$coroutines_version")

                // Ktor client
                implementation("io.ktor:ktor-client-core:$ktor_version")
                implementation("io.ktor:ktor-client-okhttp:$ktor_version") // Engine
                implementation("io.ktor:ktor-client-content-negotiation:$ktor_version")
                implementation("io.ktor:ktor-serialization-kotlinx-json:$ktor_version")

                // SQLDelight
                implementation("app.cash.sqldelight:sqlite-driver:$sqldelight_version")
                implementation("app.cash.sqldelight:coroutines-extensions:$sqldelight_version")
            }
        }
        val jvmTest by getting
    }
}


// Ejecutables de la aplicación destino
compose.desktop {
    application {
        mainClass = "MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "MyNotes"
            packageVersion = "1.0.0"
        }
    }
}

sqldelight {
    databases {
        create("AppDatabase") {
            packageName.set("database")
        }
    }
}
