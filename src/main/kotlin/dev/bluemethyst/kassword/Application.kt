package dev.bluemethyst.kassword

import com.google.gson.Gson
import dev.bluemethyst.kassword.common.loadConfig
import dev.bluemethyst.kassword.plugins.configureRouting
import io.ktor.server.application.*
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty

object Kassword {
    val GSON = Gson()
    val CONFIG = loadConfig()
}

fun main() {
    embeddedServer(
        Netty,
        port = 8080,
        module = Application::configureRouting
    ).start(wait = true)

}
