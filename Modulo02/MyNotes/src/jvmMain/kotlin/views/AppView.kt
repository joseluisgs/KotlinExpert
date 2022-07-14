package views

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.icons.filled.Mic
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.input.pointer.PointerIconDefaults
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.unit.dp
import models.Note
import mu.KotlinLogging
import states.AppState
import utils.dateParser

private val logger = KotlinLogging.logger {}

// Funcion composable inicial, todos las funciones compose deben tener esta anotacion para indicar que es una funcion composable y generar el código
// Recibe un estado general y global que hemos creado
@Composable
@Preview()
fun AppView(
    appState: AppState // Si pongo parámetros no va, la vista, por lo que le paso el singleton
) {
    val notes = appState.state.notes // Obtengo las notas

    // Para que no haga esto cada vez que actualice compose, se recomposed :)
    if (notes == null) {
        LaunchedEffect(true) {
            appState.loadNotes() // Carga las notas con el callback
        }
    }

    // Estamos con el tipo material
    MaterialTheme {
        Box(
            contentAlignment = Alignment.Center, // Todo centrado
            modifier = Modifier.fillMaxSize() // Todo el espacio disponible
        ) {
            // Progress bar
            // Cuando se recompknga si ha cambiado el estado no se pinta
            if (appState.state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.padding(16.dp),
                    color = MaterialTheme.colors.primary
                )
            }

            // Listas de notas
            if (notes != null) {
                NotesList(notes)
            }

        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun NotesList(notes: List<Note>) {
    // Crea un listado de celdas a partir de una colección de datos pudiendo reutilizar el componente de celda es un RecyclerView
    // https://developer.android.com/jetpack/compose/lists?hl=es-419
    LazyColumn(
        modifier = Modifier.fillMaxSize(), // Que ocupe todo el ancho de la pantalla
        horizontalAlignment = Alignment.CenterHorizontally // Alineamos el listado en el centro horizontalmente
    ) {
        // Recorre la colección de datos y creamos las celdas
        items(notes) { note ->

            // Para mostrar el dialogo
            var showDialog by mutableStateOf(false)
            var selectedNote by mutableStateOf<Note?>(null)

            // Crea una celda con el item que recibe del tipo Card: https://devexperto.com/cards-jetpack-compose/
            Card(
                modifier = Modifier
                    .padding(8.dp) // Añade 16dp de padding a cada lado
                    .fillMaxWidth(0.8f) // Que ocupe el 80% del ancho de la pantalla
                    .shadow(elevation = 4.dp) // Añade una sombra de 4dp a cada lado
                    // change mouse cursor to pointer when hovering over the cardAñade un icono de micrófono al cursor cuando pasa por encima de la celda
                    .pointerHoverIcon(icon = PointerIconDefaults.Hand, overrideDescendants = true)
                    // Cuando hagamos click en la celda, se muestra el dialogo con la nota seleccionada
                    .clickable {
                        // println("Has pulsado en la nota $note")
                        logger.debug { "Has pulsado en la nota $note" }
                        showDialog = true
                        selectedNote = note
                    }
            ) {
                // Para el cuadro de Alerta si es pulsado
                if (showDialog) {
                    selectedNote?.let {
                        NoteAlert(note = it,
                            showDialog = showDialog,
                            onConfirm = { showDialog = false },
                            onDismiss = { showDialog = false }
                        )
                    }
                }
                // Muestra el item que recibe en formato Columna, uno debajo de otro
                Column(
                    modifier = Modifier.padding(16.dp) // Añade 16dp de padding a cada lado
                ) {
                    // necesitamos un row si queremos varios elementos en la misma fila
                    Row {
                        // Mostramos el título de la nota
                        Text(
                            text = note.title,
                            style = MaterialTheme.typography.h6, // Estilo de texto h6
                            modifier = Modifier.weight(1f) // Que ocupe el 100% del ancho de la fila y empuja al resto
                        )
                        // Muestra el icono de micrófono solo si la nota es de deese tipo
//                            if (note.type == Note.Type.AUDIO) {
//                                Icon(
//                                    imageVector = Icons.Default.Mic,
//                                    contentDescription = "micrófono"
//                                )
//                            }
                        NoteIcon(note.type)
                    }
                    Spacer(modifier = Modifier.height(8.dp)) // Espacio entre componentes de la columna
                    Text(text = note.description) // Muestra el texto de la nota
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = note.getMoment(),
                        style = MaterialTheme.typography.caption,
                    ) // Muestra la fecha de la nota
                }

            }
        }
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
// Lo escribo en mayúsculas porque es un objeto composable
fun NoteAlert(
    note: Note,
    showDialog: Boolean,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    if (showDialog) {
        AlertDialog(
            modifier = Modifier.fillMaxWidth(0.5F),
            title = {
                Text(note.title)
            },
            text = {
                Column { // Column es una lista de elementos en una fila
                    Text(note.description)
                    Spacer(modifier = Modifier.height(8.dp))
                    NoteIcon(note.type)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(dateParser(note.createdAt))
                }
            },
            onDismissRequest = onDismiss,
            confirmButton = {
                Button(onClick = onConfirm) {
                    Text("OK")
                }
            }

        )
    }
}

@Composable
fun NoteIcon(type: Note.Type) {
    when (type) {
        Note.Type.TEXT -> {
            Icon(
                imageVector = Icons.Default.EditNote,
                contentDescription = "not de texto"
            )
        }
        Note.Type.AUDIO -> {
            Icon(
                imageVector = Icons.Default.Mic,
                contentDescription = "micrófono"
            )
        }
    }
}
