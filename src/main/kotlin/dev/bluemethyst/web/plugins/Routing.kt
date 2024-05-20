package dev.bluemethyst.web.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.io.File

fun Application.configureRouting() {
    routing {
        staticResources("/", "static")
        get("/") {
            val htmlContent = File("src/main/resources/static/html", "index.html").readText()
            call.respondText(htmlContent, ContentType.Text.Html)
        }
    }
}
