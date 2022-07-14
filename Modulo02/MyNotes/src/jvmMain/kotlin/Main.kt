// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.icons.filled.Mic
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.input.pointer.PointerIconDefaults
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Tray
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import models.Note
import mu.KotlinLogging
import states.AppState
import utils.dateParser

private val logger = KotlinLogging.logger {}

// Funcion composable inicial, todos las funciones compose deben tener esta anotacion para indicar que es una funcion composable y generar el código
// Recibe un estado general y global que hemos creado
@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterialApi::class)
@Composable
@Preview()
fun App(
    // appState: states.AppState // Si pongo parámetros no va, la vista, por lo que le paso el singleton
) {
    val appState = AppState // Si pongo parámetros no va, la vista, por lo que le paso el singleton
    // Estamos con el tipo material
    MaterialTheme {
        // Crea un listado de celdas a partir de una colección de datos pudiendo reutilizar el componente de celda es un RecyclerView
        // https://developer.android.com/jetpack/compose/lists?hl=es-419
        LazyColumn(
            modifier = Modifier.fillMaxSize(), // Que ocupe todo el ancho de la pantalla
            horizontalAlignment = Alignment.CenterHorizontally // Alineamos el listado en el centro horizontalmente
        ) {
            // Recorre la colección de datos y creamos las celdas
            items(appState.notes) { note ->

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
                            println("Has pulsado en la nota $note")
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
}


// Funcion principal Creoa una ventana que al cerrar cierra la aplicacion
fun main() = application {
    // Creamos el estado central de la aplicacion
    val appState = AppState

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
        title = "Hello Kotlin Expert",
        icon = icon
    ) {
        // llama a la función composable App
        App()
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
