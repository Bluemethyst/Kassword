package dev.bluemethyst.web.plugins

import dev.bluemethyst.web.Kassword
import dev.bluemethyst.web.net.PasswordParameters
import dev.bluemethyst.web.net.getPasswords
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.request.*
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
        post("/getPasswords") {
            val passwordParamsJson = call.receive<String>()
            val passwordParams = Kassword.GSON.fromJson(passwordParamsJson, PasswordParameters::class.java)
            val response = getPasswords(passwordParams)
            call.respondText(response)
        }
    }
}
