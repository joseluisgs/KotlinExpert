val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project
val datetime_version: String by project
val sqldelight_version: String by project
val micrologging_version: String by project
val exposed_version: String by project
val h2_jdbc_version: String by project
val koin_ktor_version: String by project
val ksp_version: String by project
val koin_ksp_version: String by project
val koin_version: String by project
val hikari_version: String by project


plugins {
    kotlin("jvm") version "1.8.10"
    id("io.ktor.plugin") version "2.2.4"
    kotlin("plugin.serialization") version "1.8.10"
    id("com.google.devtools.ksp") version "1.8.10-1.0.9" // Para Koin Annotation Processor
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

    // Exposed
    implementation("org.jetbrains.exposed:exposed-core:$exposed_version")
    implementation("org.jetbrains.exposed:exposed-dao:$exposed_version")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposed_version")
    // Kotlin data time support es multiplatorma
    implementation("org.jetbrains.exposed:exposed-kotlin-datetime:$exposed_version")

    // H2 JDBC driver
    implementation("com.h2database:h2:$h2_jdbc_version")
    // Opcionales
    // Para manejar un pool de conexions mega rápido con HikariCP (no es obligatorio)
    implementation("com.zaxxer:HikariCP:$hikari_version")

    // Logger
    implementation("io.github.microutils:kotlin-logging-jvm:$micrologging_version")

    // Koin
    implementation("io.insert-koin:koin-ktor:$koin_ktor_version") // Koin para Ktor
    implementation("io.insert-koin:koin-logger-slf4j:$koin_ktor_version") // Koin para Ktor con Logger
    implementation("io.insert-koin:koin-annotations:$koin_ksp_version") // Si usamos Koin con KSP Anotaciones
    ksp("io.insert-koin:koin-ksp-compiler:$koin_ksp_version") // Si usamos Koin con KSP Anotaciones

    // Test
    testImplementation("io.ktor:ktor-server-tests-jvm:$ktor_version")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
}

// Para Koin Annotations, directorio donde se encuentran las clases compiladas
// KSP - To use generated sources
sourceSets.main {
    java.srcDirs("build/generated/ksp/main/kotlin")
}