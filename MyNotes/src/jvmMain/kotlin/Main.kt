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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

// Funcion composable inicial, todos las funciones compose deben tener esta anotacion para indicar que es una funcion composable y generar el código
@Composable
@Preview
fun App(appState: AppState) {
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
                appState.counter.value++
                appState.text.value = when {
                    appState.counter.value % 2 == 0 -> "Haz clic \uD83D\uDE80"
                    else -> "Hola, Kotlin Expert \uD83D\uDC4B"
                }
            }) {
                // Texto del boton
                Text(appState.text.value)
            }
            Text("Has hecho click ${appState.counter.value} veces")


            // Ejercio de Strint Template
            Text("String Template")
            TextField(
                value = appState.greeting.value,
                onValueChange = {
                    appState.greeting.value = it
                }
            )
            Text(buildMessage(appState.greeting.value))
            Button(
                onClick = {
                    appState.greeting.value = ""
                },
                enabled = appState.buttonIsEnable // Lo activamos cuando no existe
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
    // Creamos el estado central de la aplicacion
    val appState = AppState()
    Window(
        onCloseRequest = ::exitApplication,
        title = "Hello Kotlin Expert"
    ) {
        // llama a la funcion composable App
        App(appState)
    }
}
