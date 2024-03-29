package views.home

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.runtime.*
import config.AppConfig
import models.Filter
import models.Note
import mu.KotlinLogging
import utils.getPlatformName

private val logger = KotlinLogging.logger {}

// Como debemos comunicarnos con fuera vamos a crear una lambda para subir el evento
@Composable
@Preview()
fun HomeTopBar(onFilterClick: (Filter) -> Unit, onCreateClick: () -> Unit) {
    TopAppBar(
        title = { Text("${AppConfig.APP_TITLE} ${getPlatformName()}") },
        // Bloque de acciones de la topAppBar
        actions = {
            // Son funciones composables, pero podríamos haber usado funciones normales
            newAction(onCreateClick)
            filterActions(onFilterClick)
        }
    )
}

@Composable
private fun filterActions(onFilterClick: (Filter) -> Unit) {
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
private fun newAction(onAddClick: () -> Unit) {
    // Boton para añadir una nota
    IconButton(onClick = {
        logger.debug("New note")
        onAddClick()
    }) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "New note"
        )
    }
}
