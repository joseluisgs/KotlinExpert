package views.home

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.runtime.*
import models.Filter
import models.Note
import utils.getPlatformName

// Como debemos comunicarnos con fuera vamos a crear una lambda para subir el evento
@Composable
fun HomeTopBar(onFilterClick: (Filter) -> Unit, onCreateClick: () -> Unit) {
    TopAppBar(
        title = { Text("My Notes ${getPlatformName()}") },
        // Bloque de acciones de la topAppBar
        actions = {
            // Son funciones composables, pero podríamos haber usado funciones normales
            NewAction(onCreateClick)
            FilterActions(onFilterClick)
        },
        backgroundColor = MaterialTheme.colors.primary,
        contentColor = MaterialTheme.colors.onPrimary
    )
}

@Composable
private fun FilterActions(onFilterClick: (Filter) -> Unit) {
    // filtrar una nota
    // para manejar el estado y se recuerde y sea observable el dropdown
    var expanded by remember { mutableStateOf(false) }

    IconButton(onClick = { expanded = !expanded }) {
        Icon(
            imageVector = Icons.Default.FilterList,
            contentDescription = "Filter notes"
        )


        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = !expanded }
        ) {

            // Para simplifcar el código usamo pares
            listOf(
                Filter.All to "All",
                Filter.ByType(Note.Type.TEXT) to "Text",
                Filter.ByType(Note.Type.AUDIO) to "Audio",
            ).forEach { (filter, text) ->
                DropdownMenuItem(onClick = {
                    onFilterClick(filter)
                    expanded = false
                }) {
                    Text(text)
                }
            }
        }
    }
}

@Composable
private fun NewAction(onAddClick: () -> Unit) {
    // Boton para añadir una nota
    IconButton(onClick = {
        onAddClick()
    }) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "New note"
        )
    }
}
