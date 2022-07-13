package states

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import models.getNotes

// Y si lo pongo como object tendría un singleton
object AppState {
    val notes by mutableStateOf(getNotes())
}