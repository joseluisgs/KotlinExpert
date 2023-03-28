package es.joseluisgs.plugins

import es.joseluisgs.routes.notesRoutes
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.html.*

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText("Hello World!")
        }

        route("/html") {
            get {
                call.respondHtml(HttpStatusCode.OK) {
                    head {
                        title { +"Ktor Server" }
                    }
                    body {
                        h1 { +"Hello from Ktor Server!" }
                        p {
                            +"Ktor Server is running"
                        }
                        div {
                            a { href = "https://ktor.io"; +"Ktor" }
                        }
                        div {
                            a { href = "/html/clicked"; +"Otra pagina" }
                        }
                    }
                }
            }
            get("/clicked") {
                call.respondHtml {
                    body {
                        h1 { +"Otra pagina" }
                    }
                }
            }
        }
    }

    // Rutas de notas
    notesRoutes()
}
