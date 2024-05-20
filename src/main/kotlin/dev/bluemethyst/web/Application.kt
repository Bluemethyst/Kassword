package dev.bluemethyst.web

import dev.bluemethyst.web.plugins.configureRouting
import io.ktor.server.application.*
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty


fun main() {
    embeddedServer(
        Netty,
        port = 8080,
        module = Application::configureRouting
    ).start(wait = true)
}
