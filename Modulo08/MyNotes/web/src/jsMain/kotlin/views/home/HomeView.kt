package views.home

import androidx.compose.runtime.Composable
import models.Note
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Text
import views.theme.AppStyleSheet


@Composable
fun HomeView(vm: HomeViewModel, onNoteClick: (noteId: Long) -> Unit) {
    Div(
        attrs = {
            classes(AppStyleSheet.homeView)
        }
    ) {

        // Top bar
        HomeTopBar(
            onFilterClick = vm::onFilterAction,
            onCreateClick = { onNoteClick(Note.NEW_NOTE) }
        )

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
        Div(
            attrs = {
                onClick { onNoteClick(Note.NEW_NOTE) }
                classes(AppStyleSheet.fabButton)
            },
        ) {
            Text("+")
        }
    }
}