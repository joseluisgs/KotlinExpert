import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import models.Note
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.H3
import org.jetbrains.compose.web.dom.Span
import org.jetbrains.compose.web.dom.Text
import org.jetbrains.compose.web.renderComposable
import views.home.HomeViewModel


fun main() {
    // Renderiza y podemos usar composables que se inyectan en el DOM en el elemento con id "root"
    renderComposable(rootElementId = "root") {
        // Creamos el ViewModel y el Scope
        val scope = rememberCoroutineScope()
        val homeViewModel = remember { HomeViewModel(scope) }

        println("Renderizando NoteList")
        NoteList(homeViewModel.state.filterNotes ?: emptyList()) { note ->
            println("Click en nota $note")
        }

    }
}


@Composable
fun NoteList(notes: List<Note>, onNoteClick: (Note) -> Unit) {
    // Dentro del Div mostramos las notas
    println("NoteList con ${notes.size} notas")
    Div {
        // Creamos un composable noteCard
        notes.forEach { note ->
            NoteCard(note, onNoteClick)
            println(note.title)
        }
    }
}

@Composable
fun NoteCard(note: Note, onNoteClick: (Note) -> Unit) {
    // Div y maquetado de la nota
    // Ahora los modificadores en Compose Desktop, en Compose Web son atributos
    println("NoteCard con ${note.title}")
    Div(
        attrs = {
            onClick { onNoteClick(note) }
        }
    ) {
        // Título de la nota
        H3 { Text(note.title) }
        // Tipo de la nota
        when (note.type) {
            Note.Type.TEXT -> {
                Span { Text("\uD83D\uDCC4") }
            }

            Note.Type.AUDIO -> {
                Span { Text("\uD83C\uDFA4") }
            }
        }
    }
}




