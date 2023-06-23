import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import models.Note
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.H3
import org.jetbrains.compose.web.dom.Text
import org.jetbrains.compose.web.renderComposable
import views.home.HomeViewModel

/*
fun main() {

    // Estados mutables
    var count: Int by mutableStateOf(0)

    // Renderiza y podemos usar composables que se inyectan en el DOM en el elemento con id "root"
    renderComposable(rootElementId = "root") {
        Div({ style { padding(25.px) } }) {
            H1 { Text("Hola ${getPlatformName()}") }
            Button(attrs = {
                onClick { count -= 1 }
            }) {
                Text("-")
            }

            Span({ style { padding(15.px) } }) {
                Text("$count")
            }

            Button(attrs = {
                onClick { count += 1 }
            }) {
                Text("+")
            }
        }
    }
}
*/

fun main() {
    // Renderiza y podemos usar composables que se inyectan en el DOM en el elemento con id "root"
    renderComposable(rootElementId = "root") {
        console.log("Hola mundo")
        // Crea un scope para usar coroutines
        val scope = rememberCoroutineScope()
        // Crea una instancia de HomeViewModel
        val homeViewModel = remember { HomeViewModel(scope) }
        // Composable que sea un listado de notas
        //NoteList(homeViewModel.state.filterNotes ?: emptyList()) { note ->
        // callback cuando se haga click
        //console.log("Click en nota $note")
        //}
        homeViewModel.state.filterNotes?.forEach { note ->
            Text(note.title)
        }
    }
}

@Composable
fun NoteList(notes: List<Note>, onNoteClick: (Note) -> Unit) {
    // Dentro del Div mostramos las notas
    Div {
        // Creamos un composable noteCard
        notes.forEach { note ->
            // NoteCard(note, onNoteClick)
            Text(note.title)
        }
    }
}

@Composable
fun NoteCard(note: Note, onNoteClick: (Note) -> Unit) {
    // Div y maquetado de la nota
    // Ahora los modificadores en Compose Desktop, en Compose Web son atributos
    Div(
        attrs = {
            onClick { onNoteClick(note) }
        }
    ) {
        // Título de la nota
        H3 { Text(note.title) }
        // Tipo de la nota
        /* when (note.type) {
             Note.Type.TEXT -> {
                 Span { androidx.compose.material.Text("\uD83D\uDCC4") }
             }

             Note.Type.AUDIO -> {
                 Span { androidx.compose.material.Text("\uD83C\uDFA4") }
             }
         }*/
    }
}




