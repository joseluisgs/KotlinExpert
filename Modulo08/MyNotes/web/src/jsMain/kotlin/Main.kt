import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import models.Note
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.css.LineStyle.Companion.Solid
import org.jetbrains.compose.web.dom.*
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
    // println("NoteList con ${notes.size} notas")
    Div(
        attrs = {
            style {
                display(DisplayStyle.Flex)
                flexDirection(FlexDirection.Column)
                alignItems(AlignItems.Center)
                gap(16.px)
                width(100.percent)
                height(100.percent)
                padding(16.px)
            }
        }
    ) {
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
    // println("NoteCard con ${note.title}")
    Div(
        attrs = {
            onClick { onNoteClick(note) }
            // Por ahora definimos los estilos así, luego será un CSS importado
            style {
                display(DisplayStyle.Flex)
                flexDirection(FlexDirection.Column)
                width(80.percent)
                maxHeight(600.px)
                border(1.px, Solid, Color.black)
                borderRadius(4.px)
                cursor("pointer")
                padding(16.px)
            }
        }
    ) {
        // Título de la nota y tipo
        Div(
            attrs = {
                style {
                    display(DisplayStyle.Flex)
                    flexDirection(FlexDirection.Row)
                    justifyContent(JustifyContent.SpaceBetween)
                    alignItems(AlignItems.Center)
                    width(100.percent)
                }
            }
        ) {
            H3 { Text(note.title) }
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




