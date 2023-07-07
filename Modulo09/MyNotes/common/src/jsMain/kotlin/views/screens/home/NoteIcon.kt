package views.screens.home

import androidx.compose.runtime.Composable
import models.Note
import views.common.Icon

@Composable
fun NoteIcon(note: Note) {
    when (note.type) {
        Note.Type.TEXT -> {
            Icon(iconName = "edit_note")
        }

        Note.Type.AUDIO -> {
            Icon(iconName = "mic")
        }
    }
}

