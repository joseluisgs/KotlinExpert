package views.detail


import androidx.compose.runtime.Composable
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Text

@Composable
fun DetailView(
    vm: DetailViewModel,
    onClose: () -> Unit,
    // onSave: () -> Unit, // No lo usamos, lo cogemos del ViewModel
    // onDelete: () -> Unit // No lo usamos, lo cogemos del ViewModel
) {
    println("DetailView de la nota ${vm.state.note}")

    // Ya tenemos la nota en el ViewModel, ya es mutableState
    val nota = vm.state.note

    Div {
        Text("$nota")
    }
}