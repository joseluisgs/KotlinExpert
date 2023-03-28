val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project
val datetime_version: String by project
val sqldelight_version: String by project

plugins {
    kotlin("jvm") version "1.8.10"
    id("io.ktor.plugin") version "2.2.4"
    kotlin("plugin.serialization") version "1.8.10"
    id("app.cash.sqldelight") version "2.0.0-alpha05"
}

group = "joseluisgs.es"
version = "0.0.1"
application {
    mainClass.set("es.joseluisgs.ApplicationKt")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    google()
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-server-core-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-netty-jvm:$ktor_version")
    implementation("ch.qos.logback:logback-classic:$logback_version")

    implementation("io.ktor:ktor-server-content-negotiation:$ktor_version")
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktor_version")

    // HTML Builder
    implementation("io.ktor:ktor-server-html-builder:$ktor_version")

    // datetime
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:$datetime_version")

    // SQLDelight JVM
    implementation("app.cash.sqldelight:sqlite-driver:$sqldelight_version")
    implementation("app.cash.sqldelight:coroutines-extensions:$sqldelight_version") // Coroutines support for queries.

    // Test
    testImplementation("io.ktor:ktor-server-tests-jvm:$ktor_version")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
}

// La base de datos
sqldelight {
    databases {
        // Nombre de la base de datos y el paquete donde se creará
        create("AppDatabase") {
            packageName.set("es.joseluisgs.database")
        }
    }
}