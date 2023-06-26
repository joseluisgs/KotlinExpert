import org.jetbrains.compose.web.css.Style
import org.jetbrains.compose.web.renderComposable
import views.app.AppView
import views.theme.AppStyleSheet


fun main() {
    // Renderiza y podemos usar composables que se inyectan en el DOM en el elemento con id "root"
    renderComposable(rootElementId = "root") {
        // Aplicamos los estilos
        Style(AppStyleSheet)

        // Cargamos la AppView
        AppView()

    }
}





