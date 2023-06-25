package views.theme

import org.jetbrains.compose.web.css.*

object AppStyleSheet : StyleSheet() {

    // Creamos una clase CSS para el contenedor de la nota
    val noteList by style {
        display(DisplayStyle.Flex)
        flexDirection(FlexDirection.Column)
        alignItems(AlignItems.Center)
        gap(16.px)
        width(100.percent)
        height(100.percent)
        padding(16.px)
    }

    // Creamos una clase CSS para la nota
    val noteCard by style {
        display(DisplayStyle.Flex)
        flexDirection(FlexDirection.Column)
        width(80.percent)
        maxHeight(600.px)
        border(1.px, LineStyle.Solid, Color.black)
        borderRadius(4.px)
        cursor("pointer")
        padding(16.px)
    }

    // Creamos una clase CSS para el encabezado de la nota
    val noteCardHeader by style {
        display(DisplayStyle.Flex)
        flexDirection(FlexDirection.Row)
        justifyContent(JustifyContent.SpaceBetween)
        alignItems(AlignItems.Center)
        width(100.percent)
    }

    // Creamos una clase CSS para el título de la nota
    val noteCardTitle by style {
        fontSize(1.5.em)
        fontWeight(700)
        color(Color.darkslategray)
        lineHeight(1.5.em)
        margin(0.px)
    }

}