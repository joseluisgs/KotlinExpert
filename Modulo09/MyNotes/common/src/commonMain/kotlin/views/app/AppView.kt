@file:Suppress("INLINE_FROM_HIGHER_PLATFORM")

package views.app

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import org.lighthousegames.logging.logging
import views.screens.home.HomeScreen

private val logger = logging()

@Composable
fun AppView() {

    logger.info { "Init AppView" }

    // Ahora navegamos con voyager
    Navigator(HomeScreen)
}