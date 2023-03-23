import org.jetbrains.compose.compose
import org.jetbrains.compose.desktop.application.dsl.TargetFormat

// Inidicamos que es multiplataforma y compose
plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
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
            kotlinOptions.jvmTarget = "11"
        }
        withJava()
    }
    sourceSets {
        val jvmMain by getting {
            dependencies {
                implementation(compose.desktop.currentOs)
                // Para iconos de la aplicación
                implementation(compose.materialIconsExtended)
                implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.0")
                // Logger
                implementation("io.github.microutils:kotlin-logging:2.1.23")
                // Salida del logger
                implementation("ch.qos.logback:logback-classic:1.3.0-alpha16")
                // Corrutinas
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-jvm:1.6.3")
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
