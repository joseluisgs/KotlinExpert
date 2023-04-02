package views.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import models.Note
import mu.KotlinLogging
import utils.dateParser

private val logger = KotlinLogging.logger {}

// Alert Dialog
@OptIn(ExperimentalMaterialApi::class)
@Composable
// Lo escribo en mayúsculas porque es un objeto composable
fun NoteAlert(
    note: Note,
    showDialog: Boolean,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
) {
    // Si el show dialog es true, lo muestro
    if (showDialog) {
        AlertDialog(
            modifier = Modifier.fillMaxWidth(0.5F),
            title = {
                Text(
                    text = note.title,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.primary,
                )
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