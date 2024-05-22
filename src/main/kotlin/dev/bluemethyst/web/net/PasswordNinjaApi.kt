package dev.bluemethyst.web.net

import dev.bluemethyst.web.Kassword
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*

data class PasswordResponse(
    val passwords: List<String>
)

data class PasswordParameters(
    val minPassLength: Int = 8,
    val animals: Boolean = true,
    val instruments: Boolean = true,
    val colours: Boolean = true,
    val shapes: Boolean = true,
    val food: Boolean = true,
    val sports: Boolean = true,
    val transport: Boolean = true,
    val symbols: Boolean = true,
    val capitals: Boolean = true,
    val numAtEnd: Int = 2,
    val randCapitals: Boolean = false,
    val lettersForNumbers: Int = 0,
    val numOfPasswords: Int = 1,
    val maxLength: Int = 20
)

suspend fun getPasswords(params: PasswordParameters): String {
    val client = HttpClient()
    val response: HttpResponse = client.get(Kassword.CONFIG.baseApiUrl) {
        url(Kassword.CONFIG.baseApiUrl)
        params.run {
            parameter("minPassLength", minPassLength)
            parameter("animals", animals)
            parameter("instruments", instruments)
            parameter("colours", colours)
            parameter("shapes", shapes)
            parameter("food", food)
            parameter("sports", sports)
            parameter("transport", transport)
            parameter("symbols", symbols)
            parameter("capitals", capitals)
            parameter("numAtEnd", numAtEnd)
            parameter("randCapitals", randCapitals)
            parameter("lettersForNumbers", lettersForNumbers)
            parameter("numOfPasswords", numOfPasswords)
            parameter("maxLength", maxLength)
        }
    }
    val responseContent = response.body<String>()
    client.close()
    return Kassword.GSON.toJson(responseContent)
}