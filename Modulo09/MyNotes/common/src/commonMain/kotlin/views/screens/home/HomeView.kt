package views.screens.home

import androidx.compose.runtime.Composable

// Para bo repetir código en las diferentes plataformas, usamos expect/actual
@Composable
expect fun HomeView(vm: HomeViewModel, onNoteClick: (noteId: Long) -> Unit)