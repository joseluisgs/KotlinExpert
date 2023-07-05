package views.detail


import androidx.compose.runtime.Composable
import models.Note
import org.jetbrains.compose.web.attributes.placeholder
import org.jetbrains.compose.web.attributes.selected
import org.jetbrains.compose.web.css.height
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.*
import org.lighthousegames.logging.logging
import views.common.Icon
import views.theme.AppStyleSheet

private val logger = logging()

@Composable
actual fun DetailView(vm: DetailViewModel, onClose: () -> Unit) {
    logger.info { "Init DetailView de la nota ${vm.state.note}" }

    // Ya tenemos la nota en el ViewModel, ya es mutableState
    val nota = vm.state.note

    Div {
        DetailTopBar(
            nota = nota,
            onClose = onClose, // Cerramos la vista
            onSave = vm::save, // Salvamos la nota
            onDelete = vm::delete // Borramos la nota
        )

        // Si la hemos salvado cerramos
        if (vm.state.saved) {
            onClose()
        }

        // Si estamos cargando mostramos un circulo de carga
        if (vm.state.isLoading) {
            Text("Cargando...")
        } else {
            // Si no mostramos el formulario de la nota
            NoteForm(vm, nota)
        }
    }
}


@Composable
private fun DetailTopBar(
    nota: Note,
    onClose: () -> Unit,
    onSave: () -> Unit,
    onDelete: () -> Unit,
) {
    Div(
        attrs = {
            classes(AppStyleSheet.topBar)
        }
    ) {

        // Botón de cerrar
        Icon(
            iconName = "close",
            attrs = {
                onClick { onClose() }
                classes(AppStyleSheet.topBarClose, AppStyleSheet.topBarIcon)
            }
        )

        // Titulo
        H1(attrs = {
            classes(AppStyleSheet.topBarTitle)
        }) {
            Text(nota.title)
        }

        // Botones de acción
        Div(
            attrs = {
                classes(AppStyleSheet.topBarActions)
            }
        ) {
            Icon(
                iconName = "save",
                attrs = {
                    onClick { onSave() }
                    classes(AppStyleSheet.topBarIcon)
                }
            )

            if (nota.id != Note.NEW_NOTE) {
                Icon(
                    iconName = "delete",
                    attrs = {
                        onClick { onDelete() }
                        classes(AppStyleSheet.topBarIcon)
                    }
                )
            }
        }
    }
}

@Composable
private fun NoteForm(vm: DetailViewModel, nota: Note) {
    Div(
        attrs = {
            classes(AppStyleSheet.formDetails)
        }
    ) {
        TextInput(
            value = nota.title,
            attrs = {
                placeholder("Título")
                onInput { vm.update(nota.copy(title = it.value)) }
            }
        )
        TypeDropDown(
            value = nota.type,
            onValueChanged = { vm.update(nota.copy(type = it)) },
        )
        TextArea(
            value = nota.description,
            attrs = {
                placeholder("Descripción")
                onInput { vm.update(nota.copy(description = it.value)) }
                style { height(200.px) }
            }
        )
    }
}

@Composable
private fun TypeDropDown(
    value: Note.Type,
    onValueChanged: (Note.Type) -> Unit
) {
    Select(
        attrs = {
            onChange { onValueChanged(Note.Type.valueOf(it.target.value)) }
        }
    ) {
        Note.Type.values().forEach {
            Option(
                value = it.name,
                attrs = {
                    if (it == value) {
                        selected()
                    }
                }
            ) {
                Text(it.name)
            }
        }
    }
}