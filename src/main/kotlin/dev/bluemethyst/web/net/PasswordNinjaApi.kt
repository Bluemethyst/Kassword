package dev.bluemethyst.web.net

import dev.bluemethyst.web.Kassword
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*

data class BooleanPasswordParameters(  //there has to be a better way to do this
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

data class PasswordParameters(
    val minPassLength: Int = 8,
    val animals: String = "true",
    val instruments: String = "true",
    val colours: String = "true",
    val shapes: String = "true",
    val food: String = "true",
    val sports: String = "true",
    val transport: String = "true",
    val symbols: String = "true",
    val capitals: String = "true",
    val numAtEnd: Int = 2,
    val randCapitals: String = "false",
    val lettersForNumbers: Int = 0,
    val numOfPasswords: Int = 1,
    val maxLength: Int = 20
) {
    fun toBooleanParameters(): BooleanPasswordParameters {
        return BooleanPasswordParameters(
            minPassLength,
            animals = animals == "on",
            instruments = instruments == "on",
            colours = colours == "on",
            shapes = shapes == "on",
            food = food == "on",
            sports = sports == "on",
            transport = transport == "on",
            symbols = symbols == "on",
            capitals = capitals == "on",
            numAtEnd,
            randCapitals = randCapitals == "on",
            lettersForNumbers,
            numOfPasswords,
            maxLength
        )
    }
}

suspend fun getPasswords(params: BooleanPasswordParameters): String {
    val client = HttpClient()
    println(params)
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
    //println(response.call.toString())
    val responseContent = response.body<String>()
    client.close()
    return Kassword.GSON.toJson(responseContent)
}