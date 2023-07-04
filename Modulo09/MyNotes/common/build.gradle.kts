val ktor_version: String by rootProject.project
val datetime_version: String by rootProject.project
val logging_version: String by rootProject.project
val logback_version: String by rootProject.project
val coroutines_version: String by rootProject.project
val sqldelight_version: String by rootProject.project
val cache_version: String by rootProject.project

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
    kotlin("plugin.serialization")
}

group = "dev.joseluisgs"
version = "1.0-SNAPSHOT"

kotlin {
    // Versión para compilación de Kotlin, esto es para usar desktop como dependencias comunes y no JVM
    jvm("desktop") {
        jvmToolchain(11) // Java 11 Si quieres Android
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
                // Api Compose para usar en todos los módulos
                api(compose.runtime)
                api(compose.foundation)
                api(compose.material)

                // Para usar iconos
                implementation(compose.materialIconsExtended)

                // Logger common
                //implementation("io.github.microutils:kotlin-logging:$logging_version")

                // Kotlin data time
                implementation("org.jetbrains.kotlinx:kotlinx-datetime:$datetime_version")

                // Ktor client
                implementation("io.ktor:ktor-client-core:$ktor_version")
                implementation("io.ktor:ktor-client-content-negotiation:$ktor_version")
                implementation("io.ktor:ktor-serialization-kotlinx-json:$ktor_version")

                // Corrutinas
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutines_version")

                // Cache4k
                implementation("io.github.reactivecircus.cache4k:cache4k:$cache_version")
            }
        }
        val commonTest by getting

        // Esto es para Desktop
        val desktopMain by getting {
            dependencies {

                // Salida del logger desktop
                //implementation("ch.qos.logback:logback-classic:$logback_version")

                // Ktor client para desktop
                implementation("io.ktor:ktor-client-okhttp:$ktor_version")
            }
        }

        // Esto pata js web
        val jsMain by getting {
            dependencies {
                // implementation(compose.web.core)
                // implementation(compose.runtime)

                // Ktor client para js
                implementation("io.ktor:ktor-client-js:$ktor_version")
            }
        }
        // Para usar tests
        val jsTest by getting
    }
}