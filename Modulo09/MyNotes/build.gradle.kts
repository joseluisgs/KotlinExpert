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
    // android compose
    kotlin("android") apply false
    id("com.android.application") apply false
    // Android Commmons
    id("com.android.library") apply false
}

