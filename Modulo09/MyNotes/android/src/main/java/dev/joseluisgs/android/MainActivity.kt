package dev.joseluisgs.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import org.lighthousegames.logging.logging
import views.app.AppView

private val logger = logging()

class MainActivity : ComponentActivity() {

    init {
        logger.info { "Init Android Client" }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Escribimos compose
        setContent {
            MaterialTheme {
                //Text("Hello world! ${getPlatformName()}")
                AppView()
            }
        }
    }
}