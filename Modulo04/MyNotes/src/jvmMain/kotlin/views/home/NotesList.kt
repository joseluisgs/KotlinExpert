package views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import models.Note
import mu.KotlinLogging

private val logger = KotlinLogging.logger {}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun NotesList(notes: List<Note>) {
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
                            text = note.title, modifier = Modifier.weight(1f), color = MaterialTheme.colors.primary,
                            style = MaterialTheme.typography.h6, // Estilo de texto h6
                            // Que ocupe el 100% del ancho de la fila y empuja al resto
                            fontWeight = FontWeight.Bold
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
