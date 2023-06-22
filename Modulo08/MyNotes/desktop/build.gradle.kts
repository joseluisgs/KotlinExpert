import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
}

group = "dev.joseluisgs"
version = "1.0-SNAPSHOT"


kotlin {
    jvm {
        jvmToolchain(17) // Java 17
        withJava()
    }
    sourceSets {
        val jvmMain by getting {
            dependencies {
                // Lo que hay en commons que me interesa
                implementation(project(":common"))
                // El especifico de desktop
                implementation(compose.desktop.currentOs)
            }
        }
        val jvmTest by getting
    }
}

compose.desktop {
    application {
        mainClass = "MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "desktop"
            packageVersion = "1.0.0"
        }
    }
}
