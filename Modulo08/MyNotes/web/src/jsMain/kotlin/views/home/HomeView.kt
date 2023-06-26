package views.home

import androidx.compose.runtime.Composable
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Text


@Composable
fun HomeView(vm: HomeViewModel, onNoteClick: (noteId: Long) -> Unit) {
    Div {

        // Top bar
        HomeTopBar(onFilterClick = vm::onFilterAction)

        // Contenido
        Div {
            if (vm.state.isLoading) {
                // Loading
                Text("Cargando...")
            } else {
                vm.state.filterNotes?.let { notes ->
                    println("Renderizando NoteList")
                    NoteList(
                        notes = notes,
                        onNoteClick = { onNoteClick(it.id) }
                    )
                }
            }
        }

        // Bottom action
        Div {
            Text("+")
        }
    }
}