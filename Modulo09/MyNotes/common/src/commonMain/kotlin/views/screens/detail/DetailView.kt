package views.screens.detail

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import org.lighthousegames.logging.logging


// Creamos un Screen para la navegación
data class DetailScreen(val idNote: Long) : Screen {
    // El logger es privado para depurar el código
    // Lo pongo aquí para que no haya conflicto con el logger de DetailView
    private val logger = logging()

    @Composable
    override fun Content() {
        // Recuperamos el navigator
        val navigator = LocalNavigator.currentOrThrow
        // Le cargamos la vista
        DetailView(
            vm = rememberScreenModel { DetailViewModel(idNote) },
            onClose = {
                logger.debug { "Navigator: Details -> Home" }
                navigator.pop()
            }
        )
    }
}

// Para no repetir código en las diferentes plataformas, usamos expect/actual

// Composable de la vista
@Composable
expect fun DetailView(vm: DetailViewModel, onClose: () -> Unit)

