package views.screens.home

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import org.lighthousegames.logging.logging
import views.screens.detail.DetailScreen


// Creamos un Screen para la navegación
object HomeScreen : Screen {
    // El logger es privado para depurar el código
    // Lo pongo aquí para que no haya conflicto con el logger de HomeView
    private val logger = logging()

    @Composable
    override fun Content() {
        // Recuperamos el navigator
        val navigator = LocalNavigator.currentOrThrow
        // Le cargamos la vista, y la recordamos
        HomeView(
            vm = rememberScreenModel { HomeViewModel() },
            onNoteClick = { idNote ->
                logger.debug { "Navigator: Home -> Details con Nota id: $idNote" }
                navigator.push(DetailScreen(idNote))
            }
        )
    }
}

// Para no repetir código en las diferentes plataformas, usamos expect/actual

// Composable de la vista
@Composable
expect fun HomeView(vm: HomeViewModel, onNoteClick: (noteId: Long) -> Unit)
