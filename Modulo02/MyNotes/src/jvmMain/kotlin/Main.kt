// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mic
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import models.Note

// Funcion composable inicial, todos las funciones compose deben tener esta anotacion para indicar que es una funcion composable y generar el código
// Recibe un estado general y global que hemos creado
@Composable
@Preview
fun App(appState: AppState) {
    // Estamos con el tipo material
    MaterialTheme {
        // Crea un listado de celdas a partir de una colección de datos pudiendo reutilizar el componente de celda es un RecyclerView
        // https://developer.android.com/jetpack/compose/lists?hl=es-419
        LazyColumn(
            modifier = Modifier.fillMaxSize(), // Que ocupe todo el ancho de la pantalla
            horizontalAlignment = Alignment.CenterHorizontally // Alineamos el listado en el centro horizontalmente
        ) {
            // Recorre la colección de datos y creamos las celdas
            items(appState.notes.value) { note ->
                // Crea una celda con el item que recibe del tipo Card: https://devexperto.com/cards-jetpack-compose/
                Card(
                    modifier = Modifier
                        .padding(8.dp) // Añade 16dp de padding a cada lado
                        .fillMaxWidth(0.8f) // Que ocupe el 80% del ancho de la pantalla
                        .shadow(elevation = 4.dp) // Añade una sombra de 4dp a cada lado
                ) {
                    // Muestra el item que recibe en formato Columna, uno debajo de otro
                    Column(
                        modifier = Modifier.padding(16.dp) // Añade 16dp de padding a cada lado
                    ) {
                        // necesitamos un row si queremos varios elementos en la misma fila
                        Row {
                            // Mostramos el título de la nota
                            Text(
                                text = note.title,
                                style = MaterialTheme.typography.h6, // Estilo de texto h6
                                modifier = Modifier.weight(1f) // Que ocupe el 100% del ancho de la fila y empuja al resto
                            )
                            // Muestra el icono de micrófono solo si la nota es de deese tipo
                            if (note.type == Note.Type.AUDIO) {
                                Icon(
                                    imageVector = Icons.Default.Mic,
                                    contentDescription = "micrófono"
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(8.dp)) // Espacio entre componentes de la columna
                        Text(text = note.description) // Muestra el texto de la nota
                    }

                }
            }
        }
    }
}


// Funcion principal Creoa una ventana que al cerrar cierra la aplicacion
fun main() = application {
    // Creamos el estado central de la aplicacion
    val appState = AppState
    Window(
        onCloseRequest = ::exitApplication,
        title = "Hello Kotlin Expert"
    ) {
        // llama a la función composable App con el estado central
        App(appState)
    }
}
