package views.home

import androidx.compose.runtime.*
import models.Filter
import models.Note
import org.jetbrains.compose.web.css.Color
import org.jetbrains.compose.web.css.color
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.H1
import org.jetbrains.compose.web.dom.Text
import utils.getPlatformName
import views.theme.AppStyleSheet

@Composable
fun HomeTopBar(onFilterClick: (Filter) -> Unit, onCreateClick: () -> Unit) {
    Div(
        attrs = {
            classes(AppStyleSheet.topBar)
        }
    ) {
        H1(
            attrs = {
                classes(AppStyleSheet.topBarTitle)
            }
        ) {
            Text("My Notes ${getPlatformName()}")
        }
        Div(
            attrs = {
                classes(AppStyleSheet.topBarActions)
            }
        ) {
            NewAction(onCreateClick)
            FilterActions(onFilterClick)
        }

    }
}

@Composable
private fun FilterActions(onFilterClick: (Filter) -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    Div(
        attrs = {
            classes(AppStyleSheet.filterAction)
        }
    ) {
        Div(
            attrs = {
                onClick { expanded = !expanded }
                style { color(Color.white) }
            }
        ) {
            Text("\uD83D\uDD0D")
        }
        // Si está expandido mostramos el menú
        if (expanded) {
            Div(
                attrs = {
                    classes(AppStyleSheet.filterActionExpanded)
                }
            ) {
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
                            classes(AppStyleSheet.filterActionExpandedItem)
                        }
                    ) {
                        Text(text)
                    }
                }
            }
        }
    }
}

@Composable
private fun NewAction(onAddClick: () -> Unit) {
    Div(
        attrs = {
            onClick { onAddClick() }
        }
    ) {
        Text("\uD83D\uDCDD")
    }
}
