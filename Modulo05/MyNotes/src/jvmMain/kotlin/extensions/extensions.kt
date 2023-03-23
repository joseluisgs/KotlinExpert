package extensions

import androidx.compose.runtime.MutableState

// Función de extensión para actualizar un value
fun <T> MutableState<T>.update(produceValue: (T) -> T) {
    value = produceValue(value)
}