package views.home

import androidx.compose.runtime.Composable


@Composable
fun HomeView(vm: HomeViewModel, onNoteClick: (noteId: Long) -> Unit) {
    // Creamos nuestra lista de notas
    println("Renderizando NoteList")
    NoteList(
        notes = vm.state.filterNotes ?: emptyList(),
        onNoteClick = { onNoteClick(it.id) }
    )
}