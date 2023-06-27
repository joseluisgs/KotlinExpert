import kotlinx.browser.document
import kotlinx.browser.window
import org.jetbrains.compose.web.css.Style
import org.jetbrains.compose.web.renderComposable
import views.app.AppView
import views.theme.AppStyleSheet


fun main() {
    composeSample()
    kotlinJsSample()
}

private fun composeSample() {
    // Renderiza y podemos usar composables que se inyectan en el DOM en el elemento con id "root"
    document.getElementById("root") ?: return
    renderComposable(rootElementId = "root") {
        // Aplicamos los estilos
        Style(AppStyleSheet)
        // Cargamos la AppView
        AppView()
    }
}

private fun kotlinJsSample() {
    // Como si fuera un main de JS
    window.onload = {
        val message = document.getElementById("message")
        if (message != null) {
            message.textContent = "Hello, Kotlin/JS!"

            val button = document.getElementById("button")!!
            button.addEventListener("click", {
                window.alert("You clicked the button!")
            })
        }
    }
}






