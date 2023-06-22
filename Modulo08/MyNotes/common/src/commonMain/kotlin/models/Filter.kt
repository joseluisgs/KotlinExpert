package models

/**
 * Interfaz Sealed para realizar filtros de búsqueda.
 */
sealed interface Filter {
    object All : Filter
    class ByType(val type: Note.Type) : Filter
}