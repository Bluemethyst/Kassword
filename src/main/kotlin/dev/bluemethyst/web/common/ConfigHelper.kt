package dev.bluemethyst.web.common

import dev.bluemethyst.web.Kassword.GSON
import java.nio.file.Files
import java.nio.file.Paths

data class KasswordConfig(
    val baseApiUrl: String
)

fun loadConfig(): KasswordConfig {
    val path = Paths.get("config.json")
    try {
        val configString = Files.readString(path)
        val configJson =  GSON.fromJson(configString, KasswordConfig::class.java)
        println("Loaded config.json successfully")
        return configJson
    } catch (e: Exception) {
        println("Failed to load config.json, using default config")
        val defaultConfig = KasswordConfig(baseApiUrl = "https://password.ninja/api/password")
        val defaultConfigJson = GSON.toJson(defaultConfig)
        Files.writeString(path, defaultConfigJson)
        return defaultConfig
    }
}