package views.screens.home

import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.icons.filled.Mic
import androidx.compose.runtime.Composable
import models.Note

// Icono de la nota
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
