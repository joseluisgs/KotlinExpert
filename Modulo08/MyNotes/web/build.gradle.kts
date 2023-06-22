plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
}

group = "dev.joseluisgs"
version = "1.0-SNAPSHOT"

repositories {
    google()
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
}

// Variante de compilación con Explorador
kotlin {
    js(IR) {
        browser {
            testTask {
                testLogging.showStandardStreams = true
                // Para usar tests
                useKarma {
                    useChromeHeadless()
                    useFirefox()
                }
            }
        }
        binaries.executable()
    }
    // Variante de compilacion de compose
    sourceSets {
        val jsMain by getting {
            dependencies {
                // Comunes
                implementation(project(":common"))
                // Específicos
                implementation(compose.web.core)
                implementation(compose.runtime)
            }
        }
        // Para usar tests
        val jsTest by getting {
            dependencies {
                implementation(kotlin("test-js"))
            }
        }
    }
}

// Registrar esta tarea para que la compilación funcione correctamente
// Workaround for https://youtrack.jetbrains.com/issue/KTIJ-16480
tasks.register("prepareKotlinBuildScriptModel"){}