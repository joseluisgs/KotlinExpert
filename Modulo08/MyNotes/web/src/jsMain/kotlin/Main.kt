import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import org.jetbrains.compose.web.css.padding
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.*
import org.jetbrains.compose.web.renderComposable
import utils.getPlatformName

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

