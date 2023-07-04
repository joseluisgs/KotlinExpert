package dev.joseluisgs.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import utils.getPlatformName

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Escribimos compose
        setContent {
            MaterialTheme {
                Text("Hello world! ${getPlatformName()}")
            }
        }
    }
}