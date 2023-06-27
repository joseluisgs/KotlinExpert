package views.home

import androidx.compose.runtime.Composable
import models.Note
import org.jetbrains.compose.web.dom.*
import views.theme.AppStyleSheet

@Composable
fun NoteList(notes: List<Note>, onNoteClick: (Note) -> Unit) {
    // Dentro del Div mostramos las notas
    // println("NoteList con ${notes.size} notas")
    Div(
        attrs = {
            classes(AppStyleSheet.noteList)
        }
    ) {
        // Creamos un composable noteCard
        notes.forEach { note ->
            NoteCard(note, onNoteClick)
        }
    }
}

@Composable
fun NoteCard(note: Note, onNoteClick: (Note) -> Unit) {
    // Div y maquetado de la nota
    // Ahora los modificadores en Compose Desktop, en Compose Web son atributos
    // println("NoteCard con ${note.title}")
    Div(
        attrs = {
            onClick { onNoteClick(note) }
            // Por ahora definimos los estilos así, luego será un CSS importado
            classes(AppStyleSheet.noteCard)
        }
    ) {
        // Título de la nota y tipo
        Div(
            attrs = {
                classes(AppStyleSheet.noteCardHeader)
            }
        ) {
            H3(
                attrs = {
                    classes(AppStyleSheet.noteCardTitle)
                }
            ) { Text(note.title) }
            NoteIcon(note)
        }
        // Descripción y momento
        P { Text(note.description) }
        Span(
            attrs = {
                classes(AppStyleSheet.noteMoment)
            }
        ) { Text(note.getMoment()) }
    }
}

