package views.common

import androidx.compose.runtime.Composable
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Text
import views.theme.AppStyleSheet


@Composable
fun ErrorMessage(message: String, onRetry: () -> Unit) {
    Div(
    ) {
        Div(
            attrs = {
                onClick { onRetry() }
                classes(AppStyleSheet.errorMessage)
            }
        ) {
            Text(message)
            Text("Reintentar")
        }
    }
}