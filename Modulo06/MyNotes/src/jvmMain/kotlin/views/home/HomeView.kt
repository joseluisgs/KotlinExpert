package views

import HomeTopBar
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import models.Note
import mu.KotlinLogging
import views.home.HomeViewModel

private val logger = KotlinLogging.logger {}

// @Composable
//fun App() = HomeView()

// Funcion composable inicial, todos las funciones compose deben tener esta anotacion para indicar que es una funcion composable y generar el código
// Recibe un estado general y global que hemos creado
// creamos la lambda on create click para avisar de los cambios
// Le pasamos el ViewModel para que sepa que tiene que hacer por constructor
@Composable
@Preview()
fun HomeView(vm: HomeViewModel, onNoteClick: (noteId: Long) -> Unit) {
    logger.debug { "HomeView" }

    // Recogemos el flow, como podemos acceder al state porque estamos haciendo un with!
    // Lo trasformamos de flow a state de compose
    // val state by state.collectAsState()

    // Ya vamos directo con el ViewModel

    // Usamos lauch effect para evitar que se repinten continuamente cuando se reconponga, e spor ello, que solo se pinta si le decimos que ha cambiado (true)
    // y por lo tanto se ejecuta el código de nuevo
    // LaunchEffect ya genera un contexto de corrutinas...
    // tambien podemos conseguir un contexto de corrutinas con
    // val scope = rememberCoroutineScope()
    // Pero no nos hace falta
    /*LaunchedEffect(true) {
        loadNotes(this) // Carga las notas con currutinas
    }*/


    // Estamos con el tipo material
    MaterialTheme {
        // Componente que nos da una estructura donde podemos añadir otros componentes de Material
        // https://developer.android.com/jetpack/compose/layouts/material#scaffold
        Scaffold(
            topBar = {
                HomeTopBar(
                    onFilterClick = vm::onFilterAction,
                    onCreateClick = { onNoteClick(Note.NEW_NOTE) }
                )
            },
            floatingActionButton = {
                FloatingActionButton(onClick = { onNoteClick(Note.NEW_NOTE) }) {
                    // Icono de añadir
                    Icon(Icons.Default.Add, contentDescription = "Add note")
                }
            }
        ) { padding ->
            // Le pasamos al padding al primer componente
            Box(
                contentAlignment = Alignment.Center, // Todo centrado
                modifier = Modifier.fillMaxSize().padding(padding) // Todo el espacio disponible
            ) {
                // Progress bar
                // Cuando se recompknga si ha cambiado el estado no se pinta
                if (vm.state.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.padding(16.dp),
                        color = MaterialTheme.colors.primary
                    )
                }

                // Listas de notas, pero ahora devolvemos las filtradas
                vm.state.filterNotes?.let { notes ->
                    NotesList(notes, onNoteClick = { onNoteClick(it.id) })
                }

            }
        }

    }
}





