package views.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Save
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import models.Note
import mu.KotlinLogging

private val logger = KotlinLogging.logger {}

@Composable
fun DetailView(
    id: Long,
    onClose: () -> Unit,
    onSave: () -> Unit,
    onDelete: () -> Unit
) {
    logger.debug { "DetailView $id" }

    var nota by remember {
        mutableStateOf(
            Note(
                id = id,
                title = "",
                description = "",
                type = Note.Type.TEXT
            )
        )
    }

    Scaffold(
        topBar = {
            DetailTopBar(
                nota = nota,
                onClose = onClose,
                onSave = onSave,
                onDelete = onDelete
            )
        }
    ) {
        // Formulario
        Column(modifier = Modifier.padding(32.dp)) {
            OutlinedTextField(
                value = nota.title,
                onValueChange = { nota = nota.copy(title = it) },
                label = { Text("Titulo") },
                modifier = Modifier.fillMaxWidth(),
                maxLines = 1
            )
            TypeDropDown(
                value = nota.type,
                onValueChanged = { nota = nota.copy(type = it) },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = nota.description,
                onValueChange = { nota = nota.copy(description = it) },
                label = { Text("Descripción") },
                modifier = Modifier.fillMaxWidth().weight(1f)
            )
        }
    }
}

@Composable
private fun TypeDropDown(value: Note.Type, onValueChanged: (Note.Type) -> Unit, modifier: Modifier) {

    var expanded by remember { mutableStateOf(false) }

    Box(modifier = modifier, propagateMinConstraints = true) {
        OutlinedTextField(
            value = value.toString(),
            onValueChange = { /* TODO */ },
            readOnly = true,
            label = { Text("Tipo") },
            trailingIcon = {
                // Cambiar el icono cuando se expanda
                IconButton(onClick = { expanded = true }) {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Mostrar tipo de notas"
                    )
                }
            }
        )
        // Dropdown de tipos de notas
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            Note.Type.values().forEach {
                DropdownMenuItem(onClick = { onValueChanged(it); expanded = false }) {
                    Text(it.toString())
                }
            }
        }
    }
}

@Composable
private fun DetailTopBar(
    nota: Note,
    onClose: () -> Unit,
    onSave: () -> Unit,
    onDelete: () -> Unit
) {
    TopAppBar(
        title = { Text(text = "Nota: ${nota.title}") },
        navigationIcon = {
            IconButton(onClick = onClose) {
                Icon(imageVector = Icons.Default.Close, contentDescription = "Cerrar")
            }
        },
        actions = {
            IconButton(onClick = onSave) {
                Icon(imageVector = Icons.Default.Save, contentDescription = "Salvar")
            }
            if (nota.id != Note.NEW_NOTE) {
                IconButton(onClick = onDelete) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = "Eliminar")
                }
            }
        }
    )
}