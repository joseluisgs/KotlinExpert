package views.app

import androidx.compose.runtime.*
import models.Note
import mu.KotlinLogging
import views.HomeView
import views.detail.DetailView

private val logger = KotlinLogging.logger {}

@Composable
fun AppView() {
    logger.debug { "AppView" }
    // Lo primero es almacenar la ruta actual, y lo cargamos en el HomeView
    var route by remember { mutableStateOf<Route>(Route.Home) }

    // Rutas de navegación, constantes y rutas
    route.let {
        logger.debug { "Route: $it" }
        when (it) {
            // Cuando es Home, cargamos el HomeView y si pulsamos el botón de crear, cambiamos la ruta a Detail con id -1
            is Route.Home -> HomeView(
                onCreateClick = { route = Route.Detail(Note.NEW_NOTE) }
            )

            // Cuando es Detail, cargamos el DetailView y le pasamos el id
            is Route.Detail -> DetailView(it.id,
                onClose = { route = Route.Home },
                onSave = { /* TODO */ },
                onDelete = { /* TODO */ }
            )
        }
    }
}