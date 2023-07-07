package views.screens.detail

import androidx.compose.runtime.Composable


// Para no repetir código en las diferentes plataformas, usamos expect/actual
// Esto es porque en Web es específica

// Composable de la vista
@Composable
expect fun DetailView(vm: DetailViewModel, onClose: () -> Unit)

