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

// Función composable que recibe un ViewModel
// y una función lambda para saber como actuar con el clic en base a su id que recibe
// un id de nota y no devuelve nada

@Composable
@Preview()
fun HomeView(vm: HomeViewModel, onNoteClick: (noteId: Long) -> Unit) {
    logger.debug { "HomeView" }

    // Estilo de Material
    MaterialTheme {
        // Componente que nos da una estructura donde podemos añadir otros componentes de Material
        // https://developer.android.com/jetpack/compose/layouts/material#scaffold
        Scaffold(
            // La topBar es un componente que se pinta en la parte superior
            topBar = {
                HomeTopBar(
                    onFilterClick = vm::onFilterAction,
                    onCreateClick = { onNoteClick(Note.NEW_NOTE) } // Llamamos a la función lambda que nos pasan
                )
            },

            // El floatingActionButton es un componente que se pinta en la parte inferior derecha
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

                // Progress bar, solo si en el estado hay que pintarla
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
