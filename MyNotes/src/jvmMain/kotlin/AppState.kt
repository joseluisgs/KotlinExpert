import androidx.compose.runtime.mutableStateOf

class AppState {
    // Estado de toda la App
    // Se crea una variable de estado, con remebeer recuerda el estado que ha tenido o modificado para la recomposición

    // Como estamos fuera de un entorno de composición, podemos omitir el remember
    val text = mutableStateOf("Hola Mundo!")
    val counter = mutableStateOf(0)
    val greeting = mutableStateOf("")
    val buttonIsEnable: Boolean
        get() = greeting.value.isNotEmpty()
}