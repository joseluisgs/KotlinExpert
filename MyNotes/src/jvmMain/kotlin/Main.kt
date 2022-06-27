// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

// Funcion composable inicial, todos las funciones compose deben tener esta anotacion para indicar que es una funcion composable y generar el código
@Composable
@Preview
fun App() {
    // Se crea una variable de estado, con remebeer recuerda el estado que ha tenido o modificado para la recomposición
    var text by remember { mutableStateOf("Hola, Mundo!") } // by es un delegado
    var counter by remember { mutableStateOf(0) }

    var greeting by remember { mutableStateOf("") }
    val buttonIsEnable = greeting.isNotEmpty()

    // Se fija el estado inicial
    MaterialTheme {
        // Boton, al hacer click se cambia el texto
        Column(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth()
                .background(Color.LightGray),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(onClick = {
                counter++
                text = when {
                    counter % 2 == 0 -> "Haz clic \uD83D\uDE80"
                    else -> "Hola, Kotlin Expert \uD83D\uDC4B"
                }
            }) {
                // Texto del boton
                Text(text)
            }
            Text("Has hecho click $counter veces")


            // Ejercio de Strint Template
            Text("String Template")
            TextField(
                value = greeting,
                onValueChange = {
                    greeting = it
                }
            )
            Text(buildMessage(greeting))
            Button(
                onClick = {
                    greeting = ""
                },
                enabled = buttonIsEnable // Lo activamos cuando no existe
            ) {
                // Texto del boton
                Text("Limpiar")
            }
        }
    }
}

fun buildMessage(greeting: String) = "Hola, $greeting"

// Funcion principal Creoa una ventana que al cerrar cierra la aplicacion
fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Hello Kotlin Expert"
    ) {
        // llama a la funcion composable App
        App()
    }
}
