val ktor_version: String by rootProject.project
val datetime_version: String by rootProject.project
val coroutines_version: String by rootProject.project
val cache_version: String by rootProject.project
val logger_version: String by rootProject.project
val logback_version: String by rootProject.project

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
    kotlin("plugin.serialization")
    id("com.android.library")
}

group = "dev.joseluisgs"
version = "1.0-SNAPSHOT"

kotlin {
    // Versión para compilación de Kotlin, esto es para usar desktop como dependencias comunes y no JVM
    jvm("desktop") {
        jvmToolchain(11) // Java 11 Si quieres Android y Desktop
    }
    // JS
    js(IR) {
        browser()
    }
    // Android
    android {
        // Configuración de Android
        jvmToolchain(11) // Java 11 Si quieres Android y Desktop
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

                // Logger
                api("org.lighthousegames:logging:$logger_version")

            }
        }
        val commonTest by getting

        // Esto es para los que cmparten una logica KMP
        // Se crea porque no existe, y se le añade dependencia de commonMain
        val commonComposeKmpMain by creating {
            dependsOn(commonMain)
        }

        // Esto es para Desktop
        val desktopMain by getting {
            dependencies {
                // Ahora le decimos que depende de commonMain
                dependsOn(commonComposeKmpMain)

                // Ktor client para desktop
                implementation("io.ktor:ktor-client-okhttp:$ktor_version")

                // Corrutinas Swing Desktop
                // implementation("org.jetbrains.kotlinx:kotlinx-coroutines-swing:$coroutines_version")

                // Logger JVV
                implementation("ch.qos.logback:logback-classic:$logback_version")

            }
        }
        val desktopTest by getting

        // Esto pata js web
        val jsMain by getting {
            dependencies {
                implementation(compose.html.core)

                // Ktor client para js
                implementation("io.ktor:ktor-client-js:$ktor_version")
            }
        }
        // Para usar tests
        val jsTest by getting

        // Esto es para Android
        val androidMain by getting {
            dependencies {
                // Ahora le decimos que depende de commonMain
                dependsOn(commonComposeKmpMain)

                // Ktor client para Android
                implementation("io.ktor:ktor-client-okhttp:$ktor_version")

                // Corrutinas para Android
                // implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutines_version")
            }
        }
        // Para usar tests
        val androidTest by getting
    }
}

// Para android
android {
    compileSdk = 33
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdk = 24
        targetSdk = 33
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}