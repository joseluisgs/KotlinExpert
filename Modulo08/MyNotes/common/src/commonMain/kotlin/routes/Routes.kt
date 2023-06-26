package routes

// Mis Rutas de navegación, lo hacemos con un sealed class
sealed interface Route {
    object Home : Route
    data class Detail(val id: Long) : Route

}