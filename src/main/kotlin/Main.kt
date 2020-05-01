import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import freemarker.cache.ClassTemplateLoader
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.gson.gson
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import java.io.File
import freemarker.cache.*
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.freemarker.*

// represents the json data obtained from the .json file
var data: CovidData? = null

// functions to get json from string and vice versa
fun CovidData.toJson(): String = ObjectMapper().writeValueAsString(this)
fun String.toCovidData(): CovidData = ObjectMapper().readValue(this)

// gets and displays required covid data
fun Application.dataGetter() {
    install(io.ktor.features.ContentNegotiation) {
        gson {}
    }
    routing {
        get("/") {
            call.respondText("In the URL, specify the following: \n" +
                "1. Country (US only, more will be added )\n" +
                "2. Month (1-12)\n" +
                "3. day of the month (1-30)")
        }
        get("/{country}/{month}/{date}") {
            try {
                val country = call.parameters["country"]!!.toString()
                val month = call.parameters["month"]!!.toInt()
                val date = call.parameters["date"]!!.toInt()

                if (!country.equals("US")) {
                    call.respond("Sorry, only data for the US is available.")
                }

                if (month > 12 || month <= 0 || date > 31 || date <= 0)
                    throw java.lang.Exception("Invalid input")

                val requiredData: CountryData? = getRequiredData(date, month)

                if (requiredData == null) {
                    // this means no data could be found for the requested date and month.
                    call.respond("Either an invalid request, or no data exists for the requested date-month parameters.")
                } else {
                    call.respond("Date: " + requiredData.getDate().toString() +
                                "\nConfirmed: " + requiredData.getConfirmed().toString() +
                                "\nRecovered: " + requiredData.getRecovered().toString() +
                                "\nDeaths: " + requiredData.getDeaths().toString())
                }
            } catch(e: Exception) {
                call.respond(HttpStatusCode.BadRequest)
            }
        }
    }
}

/**
 * Gets the json from file
 * @param filepath path of the file as a String
 * @return A CovidData object read from the file
 */
fun getJsonFromFile(filepath: String): CovidData {
    val jsonString: String = File(filepath).readText(Charsets.UTF_8)
    println(jsonString)
    data = jsonString.toCovidData()
    return data as CovidData
}

/**
 * Gets the required data for the month and date from the json in the file
 * @param date the date/day of the month (1-31)
 * @param month month of the year (1-12)
 * @return a CountryData object for the date and month passed to the function
 *          and null if no data exists for the given date and month.
 */
fun getRequiredData(date: Int, month: Int): CountryData? {
    val countryData: List<CountryData?>? = data?.getUS()
    if (countryData != null) {
        for (country_data: CountryData? in countryData) {
            if (country_data != null) {
                if (country_data.getDate().equals("2020-" + month.toString() + "-" + date.toString())) {
                    return country_data
                }
            }
        }
    }
    return null
}

fun main() {
    getJsonFromFile("./src/main/resources/data.json")
    embeddedServer(Netty, port = 8012, module = Application::dataGetter).start(wait = true)
}
