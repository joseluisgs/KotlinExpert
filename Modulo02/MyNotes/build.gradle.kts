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
