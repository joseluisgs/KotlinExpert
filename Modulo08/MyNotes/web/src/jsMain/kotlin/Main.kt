import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import models.Note
import org.jetbrains.compose.web.css.Style
import org.jetbrains.compose.web.dom.*
import org.jetbrains.compose.web.renderComposable
import views.home.HomeViewModel
import views.theme.AppStyleSheet


fun main() {
    // Renderiza y podemos usar composables que se inyectan en el DOM en el elemento con id "root"
    renderComposable(rootElementId = "root") {
        // Aplicamos los estilos
        Style(AppStyleSheet)

        // Creamos el ViewModel y el Scope
        val scope = rememberCoroutineScope()
        val homeViewModel = remember { HomeViewModel(scope) }

        // Creamos nuestra lista de notas
        println("Renderizando NoteList")
        NoteList(homeViewModel.state.filterNotes ?: emptyList()) { note ->
            println("Click en nota $note")
        }
    }
}


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
            when (note.type) {
                Note.Type.TEXT -> {
                    Span { Text("\uD83D\uDCC4") }
                }

                Note.Type.AUDIO -> {
                    Span { Text("\uD83C\uDFA4") }
                }
            }
        }
        // Descripción y momento
        P { Text(note.description) }
        Span { Text(note.getMoment()) }
    }
}




