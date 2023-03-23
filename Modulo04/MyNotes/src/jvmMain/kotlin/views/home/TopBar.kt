import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.runtime.*
import models.Filter
import models.Note
import mu.KotlinLogging
import utils.ConfigProperties

private val logger = KotlinLogging.logger {}

// Como debemos comunicarnos con fuera vamos a crear una lambda para subir el evento
@Composable
@Preview()
fun TopBar(onFilterClick: (Filter) -> Unit) {
    TopAppBar(
        title = { Text(ConfigProperties.getProperty("app.title")) },
        // Bloque de acciones de la topAppBar
        actions = {
            newAction { } // TODO
            filterActions(onFilterClick)
        }
    )
}

@Composable
private fun filterActions(onFilterClick: (Filter) -> Unit) {
    // filtrar una nota
    // para manejar el estado y se recuerde y sea observable el dropdown
    var expanded by remember { mutableStateOf(false) }

    // Vamos a crear una función local paar practicar
    // ademas infija
    // Es un poco loco, por eso lo deshacemos
    /*@Composable
    infix fun Filter.toMenuItem(label: String) {
        DropdownMenuItem(onClick = {
            expanded = false
            onFilterClick(this)
        }) {
            Text(label)
        }
    }*/

    IconButton(onClick = { expanded = !expanded }) {
        Icon(
            imageVector = Icons.Default.FilterList,
            contentDescription = "Filter notes"
        )


        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = !expanded }
        ) {
            /*// Usamos nuestra nueva función local infija
            Filter.All toMenuItem "All"
            Filter.ByType(Note.Type.TEXT) toMenuItem "Text"
            Filter.ByType(Note.Type.AUDIO) toMenuItem "Audio"*/

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
