package views.theme

import org.jetbrains.compose.web.css.*

object AppStyleSheet : StyleSheet() {
    // estiilos por defecto
    init {
        "*" style {
            fontFamily("Verdana", "sans-serif")
        }

        "body" style {
            margin(0.px)
        }
    }


    // Estilos para HomeView
    val homeView by style {
        display(DisplayStyle.Flex)
        flexDirection(FlexDirection.Column)
        alignItems(AlignItems.Center)
        width(100.percent)
        height(100.percent)
    }

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
        width(100.percent)
        maxHeight(600.px)
        border(1.px, LineStyle.Solid, Color.darkgray)
        borderRadius(4.px)
        cursor("pointer")
        padding(16.px)
        //fontFamily("sans-serif")
        property("box-shadow", "7px 8px 11px -5px rgba(0,0,0,0.56)")
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
        color(Color("#6200EE"))
        lineHeight(1.5.em)
        margin(0.px)
    }

    // Creamos una clase CSS para el momento
    val noteMoment by style {
        fontSize(0.8.em)
        fontWeight(400)
        //color(Color.darkgray)
    }

    // Creamos una clase CSS para el contenido de la nota
    val fabButton by style {
        display(DisplayStyle.Flex)
        justifyContent(JustifyContent.Center)
        alignItems(AlignItems.Center)
        position(Position.Fixed)
        bottom(16.px)
        right(16.px)
        width(64.px)
        height(64.px)
        borderRadius(50.percent)
        backgroundColor(Color("#6200EE"))
        color(Color.white)
        fontSize(24.px)
        lineHeight(1.em)
        cursor("pointer")
        property("box-shadow", "0px 5px 5px 0px rgba(0,0,0,0.4)")
        self + hover style {
            backgroundColor(Color("#6200eecc"))
        }
    }

    // Para la top bar
    val topBar by style {
        display(DisplayStyle.Flex)
        flexDirection(FlexDirection.Row)
        justifyContent(JustifyContent.SpaceBetween)
        alignItems(AlignItems.Center)
        width(100.percent)
        height(64.px)
        backgroundColor(Color("#6200EE")) // Mirar si se puede poner un color de Material
        color(Color.white)
        property("box-shadow", "0px 0px 4px 0px rgba(0,0,0,0.25)")
    }

    val topBarTitle by style {
        color(Color.white)
        margin(0.px)
        fontSize(25.px)
        fontWeight(700)
        paddingLeft(16.px)
        flex(1)
    }

    val topBarActions by style {
        display(DisplayStyle.Flex)
        flexDirection(FlexDirection.Row)
        justifyContent(JustifyContent.SpaceBetween)
        alignItems(AlignItems.Center)
        gap(16.px)
        paddingRight(16.px)
    }

    val topBarClose by style {
        marginLeft(16.px)
    }

    val topBarIcon by style {
        color(Color.white)
        cursor("pointer")
        borderRadius(50.percent)
        padding(12.px)
        self + hover style {
            backgroundColor(Color("#ffffff4d"))
        }
    }

    // Estilos de filter
    val filterAction by style {
        cursor("pointer")
    }

    val filterActionExpanded by style {
        display(DisplayStyle.Flex)
        flexDirection(FlexDirection.Column)
        alignItems(AlignItems.Center)
        backgroundColor(Color.white)
        borderRadius(4.px)
        position(Position.Absolute)
        top(48.px)
        right(16.px)
        property("box-shadow", "0px 0px 4px 0px rgba(0,0,0,0.25)")
        property("z-index", "1")
        color(Color.black)
    }

    val filterActionExpandedItem by style {
        justifyContent(JustifyContent.Center)
        alignItems(AlignItems.Center)
        padding(16.px)
        minWidth(150.px)
        cursor("pointer")
    }

    // Estilos formulario
    val formDetails by style {
        display(DisplayStyle.Flex)
        flexDirection(FlexDirection.Column)
        gap(16.px)
        width(70.percent)
        maxWidth(600.percent)
        padding(32.px)
        property("margin", "0 auto")
    }

    val errorMessage by style {
        color(Color.red)
        fontSize(0.8.em)
        fontWeight(400)
        cursor("pointer")
    }

}