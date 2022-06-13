// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

// Funcion composable inicial
@Composable
@Preview
fun App() {
    // Se crea una variable de estado
    var text by remember { mutableStateOf("Hola, Mundo!") }
    var counter by remember { mutableStateOf(0) }

    // Se fija el estado inicial
    MaterialTheme {
        // Boton, al hacer click se cambia el texto
        Column (
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth()
                .background(Color.Gray),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(onClick = {
                counter++
                when {
                    counter % 2 == 0 -> text = "Haz clic \uD83D\uDE80"
                    else -> text = "Hola, Kotlin Expert \uD83D\uDC4B"
                }
            }) {
                // Texto del boton
                Text(text)
            }
            Text("Has hecho click $counter veces")

        }
    }
}

// Funcion principal Creoa una ventana que al cerrar cierra la aplicacion
fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Hello Kotlin Expert") {
        // llama a la funcion composable App
        App()
    }
}
