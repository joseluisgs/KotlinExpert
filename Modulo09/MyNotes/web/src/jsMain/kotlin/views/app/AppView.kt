package views.app

import androidx.compose.runtime.*
import org.lighthousegames.logging.logging
import routes.Route
import views.detail.DetailView
import views.detail.DetailViewModel
import views.home.HomeView
import views.home.HomeViewModel

private val logger = logging()

@Composable
fun AppView() {

    logger.info { "Init App View" }

    // Lo primero es almacenar la ruta actual, y lo cargamos en el HomeView
    var route by remember { mutableStateOf<Route>(Route.Home) }
    // Creamos el scope para las corrutinas y viewModel
    val scope = rememberCoroutineScope()

    // Rutas de navegación, constantes y rutas
    route.let {
        when (it) {
            // Cuando es Home, cargamos el HomeView y si pulsamos el botón de crear, cambiamos la ruta a Detail con id -1
            is Route.Home -> HomeView(
                vm = HomeViewModel(scope),
                onNoteClick = { noteId -> route = Route.Detail(noteId) }
            )

            // Cuando es Detail, cargamos el DetailView y le pasamos el id
            is Route.Detail -> DetailView(
                vm = DetailViewModel(scope, it.id),
                onClose = { route = Route.Home },
                // Estos me pueden sobrar si los hago en el DetailViewModel
                //onSave = { /* TODO */ },
                //onDelete = { /* TODO */ }
            )
        }
    }

}

