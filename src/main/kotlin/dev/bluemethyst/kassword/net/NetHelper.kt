package dev.bluemethyst.kassword.net

import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

val client: HttpClient = HttpClient.newBuilder().build()

fun get(url: String, headers: Map<String, String> = emptyMap()): HttpResponse<String> {
    val request = HttpRequest.newBuilder()
        .uri(URI.create(url))
        .apply {
            headers.forEach { (key, value) -> header(key, value) }
        }
        .build()
    val response = client.send(request, HttpResponse.BodyHandlers.ofString())
    return response
}

fun post(url: String, body: String, headers: Map<String, String> = emptyMap()): HttpResponse<String>? {
    val request = HttpRequest.newBuilder()
        .uri(URI.create(url))
        .POST(HttpRequest.BodyPublishers.ofString(body))
        .apply {
            headers.forEach { (key, value) -> header(key, value) }
        }
        .build()
    val response = client.send(request, HttpResponse.BodyHandlers.ofString())
    return response
}