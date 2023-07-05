// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.window.Tray
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import config.AppConfig
import org.lighthousegames.logging.logging
import utils.getPlatformName
import views.app.AppView

private val logger = logging()

// application es una función de alto nivel que nos permite crear una aplicación de escritorio
// y usar composables
fun main() = application {

    logger.info { "Init Desktop Client" }

    // Icono de la aplicación
    val icon = painterResource("app-icon.png")

    // Pone un icono de Try en la barra de tareas
    Tray(
        icon = icon,
        menu = {
            Item("¿Salir de MyNotes?", onClick = ::exitApplication)
        }
    )
    // Crea una ventana
    Window(
        onCloseRequest = ::exitApplication,
        title = AppConfig.APP_NAME + getPlatformName(),
        icon = icon
    ) {
        // Carga la vista principal que es el composablee AppView
        AppView()
    }
}
