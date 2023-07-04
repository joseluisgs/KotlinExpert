package views.home

import androidx.compose.runtime.*
import models.Filter
import models.Note
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.H1
import org.jetbrains.compose.web.dom.Text
import utils.getPlatformName
import views.common.Icon
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
        Icon(
            iconName = "filter_list",
            attrs = {
                onClick { expanded = !expanded }
                classes(AppStyleSheet.topBarIcon)
            }
        )
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
    Icon(
        iconName = "add",
        attrs = {
            onClick { onAddClick() }
            classes(AppStyleSheet.topBarIcon)
        }
    )
}
