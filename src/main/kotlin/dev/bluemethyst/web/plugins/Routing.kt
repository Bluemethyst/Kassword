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
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.PrintWriter

data class PasswordResponse(
    val passwords: List<String>
)

fun Application.configureRouting() {
    routing {
        staticResources("/", "static")
        get("/") {
            val htmlContent = File("src/main/resources/static/html", "index.html").readText()
            call.respondText(htmlContent, ContentType.Text.Html)
        }
        post("/getPasswords") {
            val passwordParamsJson = call.receive<String>()
            val passwordParams = Kassword.GSON.fromJson(passwordParamsJson, PasswordParameters::class.java).toBooleanParameters()
            val response = getPasswords(passwordParams)
            call.respondText(response)
        }
        downloadPasswords()
    }
}
fun Route.downloadPasswords() {
    post("/downloadPasswords") {
        val passwords = call.receive<String>()
        val passwordResponse = Kassword.GSON.fromJson(passwords, PasswordResponse::class.java)
        val outputStream = ByteArrayOutputStream()
        PrintWriter(outputStream).use { writer ->
            passwordResponse.passwords.forEach { password ->
                writer.println(password)
            }
        }
        val csvBytes = outputStream.toByteArray()
        call.response.header(HttpHeaders.ContentDisposition, ContentDisposition.Attachment.withParameter(ContentDisposition.Parameters.FileName, "passwords.csv").toString())
        call.respondBytes(csvBytes, status = HttpStatusCode.OK)
    }
}