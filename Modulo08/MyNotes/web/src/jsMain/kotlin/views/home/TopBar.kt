package views.home

import androidx.compose.runtime.*
import models.Filter
import models.Note
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.H1
import org.jetbrains.compose.web.dom.Text
import utils.getPlatformName

@Composable
fun HomeTopBar(onFilterClick: (Filter) -> Unit) {
    Div {
        H1 {
            Text("My Notes ${getPlatformName()}")
        }
        filterActions(onFilterClick)
    }
}

@Composable
private fun filterActions(onFilterClick: (Filter) -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    Div {
        Div(
            attrs = {
                onClick { expanded = !expanded }
            }
        ) {
            Text("\uD83D\uDD0D")
        }
        // Si está expandido mostramos el menú
        if (expanded) {
            Div {
                listOf(
                    Filter.All to "All",
                    Filter.ByType(Note.Type.TEXT) to "Text",
                    Filter.ByType(Note.Type.AUDIO) to "Audio",
                ).forEach { (filter, text) ->
                    Div(
                        attrs = {
                            onClick {
                                onFilterClick(filter)
                                expanded = false
                            }
                        }
                    ) {
                        Text(text)
                    }
                }
            }
        }
    }
}