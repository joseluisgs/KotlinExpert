package routes

// Mis Rutas de navegación
sealed interface Route {
    object Home : Route
    data class Detail(val id: Long) : Route

}