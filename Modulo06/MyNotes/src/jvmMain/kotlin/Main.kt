// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.window.Tray
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import config.AppConfig
import views.app.AppView


// Funcion principal Creoa una ventana que al cerrar cierra la aplicacion
fun main() = application {

    // Icono de la aplicacion
    val icon = painterResource("app-icon.png")

    // Pone un icono de Try
    Tray(
        icon = icon,
        menu = {
            Item("¿Salir de MyNotes?", onClick = ::exitApplication)
        }
    )
    // Configuramos la ventana
    Window(
        onCloseRequest = ::exitApplication,
        title = AppConfig.APP_NAME,
        icon = icon
    ) {
        // llama a la función composable App
        AppView()
    }
}
