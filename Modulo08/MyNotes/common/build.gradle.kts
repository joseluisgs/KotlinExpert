val ktor_version: String by rootProject.project
val datetime_version: String by rootProject.project
val logging_version: String by rootProject.project
val logback_version: String by rootProject.project
val coroutines_version: String by rootProject.project
val sqldelight_version: String by rootProject.project

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
    kotlin("plugin.serialization")
    id("app.cash.sqldelight")
}

group = "dev.joseluisgs"
version = "1.0-SNAPSHOT"

kotlin {
    // Versión para compilación de Kotlin, esto es para usar desktop como dependencias comunes y no JVM
    jvm("desktop") {
        jvmToolchain(17) // Java 17
        withJava()
    }
    // JS
    js(IR) {
        browser()
    }

    // SourceSet, cada uno en su carpeta dentro de src
    sourceSets {

        // Esto es para todos!!
        val commonMain by getting {
            // Para compose genérico
            dependencies {
                // Logger common
                implementation("io.github.microutils:kotlin-logging:$logging_version")

                // Kotlin data time
                implementation("org.jetbrains.kotlinx:kotlinx-datetime:$datetime_version")

                // Ktor client
                implementation("io.ktor:ktor-client-core:$ktor_version")
                // implementation("io.ktor:ktor-client-okhttp:$ktor_version") // Engine
                implementation("io.ktor:ktor-client-content-negotiation:$ktor_version")
                implementation("io.ktor:ktor-serialization-kotlinx-json:$ktor_version")

                // Corrutinas
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutines_version")

                // SQLDelight Common
                implementation("app.cash.sqldelight:coroutines-extensions:$sqldelight_version")
            }
        }
        val commonTest by getting

        // Esto es para Desktop
        val desktopMain by getting {
            dependencies {
                implementation(compose.desktop.currentOs)
                // Para iconos de la aplicación
                implementation(compose.materialIconsExtended)

                // Salida del logger desktop
                implementation("ch.qos.logback:logback-classic:$logback_version")

                // SQLDelight Desktop
                implementation("app.cash.sqldelight:sqlite-driver:$sqldelight_version")

                // Ktor client
                implementation("io.ktor:ktor-client-okhttp:$ktor_version")
            }
        }

        // Esto pata js web
        val jsMain by getting {
            dependencies {
                implementation(compose.web.core)
                implementation(compose.runtime)
                implementation("io.ktor:ktor-client-js:$ktor_version")
            }
        }
        // Para usar tests
        val jsTest by getting
    }
}

// SQLDelight, Nombre de la base de datos y paquete de destino
sqldelight {
    databases {
        create("AppDatabase") {
            packageName.set("database")
        }
    }
}

