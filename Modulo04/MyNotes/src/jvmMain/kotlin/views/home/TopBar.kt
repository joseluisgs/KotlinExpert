import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.runtime.*
import mu.KotlinLogging
import utils.ConfigProperties

private val logger = KotlinLogging.logger {}

@Composable
@Preview()
fun TopBar() {
    TopAppBar(
        title = { Text(ConfigProperties.getProperty("app.title")) },
        // Bloque de acciones de la topAppBar
        actions = {
            // Boton para añadir una nota
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add note"
                )
            }
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
                    // Cada elemento del menú
                    DropdownMenuItem(onClick = { expanded = !expanded }) {
                        Text("All")
                    }
                    DropdownMenuItem(onClick = { expanded = !expanded }) {
                        Text("Text")
                    }
                    DropdownMenuItem(onClick = { expanded = !expanded }) {
                        Text("Audio")
                    }
                }
            }
        }
    )
}