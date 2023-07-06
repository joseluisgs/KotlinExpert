package views.screens.detail

import androidx.compose.runtime.Composable

// Para bo repetir código en las diferentes plataformas, usamos expect/actual
@Composable
expect fun DetailView(vm: DetailViewModel, onClose: () -> Unit)