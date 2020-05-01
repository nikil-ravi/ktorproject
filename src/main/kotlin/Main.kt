import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.google.gson.JsonObject
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
import java.util.HashMap


var data: CovidData? = null;

fun CovidData.toJson(): String = ObjectMapper().writeValueAsString(this)

fun String.toCovidData(): CovidData = ObjectMapper().readValue(this)

fun Application.dataGetter() {
    install(io.ktor.features.ContentNegotiation) {
        gson {}
    }
    routing {
        get("/") {
            call.respondText("In the URL, specify the following: \n" +
                "1. Country\n" +
                "2. Month (1-12)\n" +
                "3. day of the month (1-30)")
        }
        get("/{country}/{month}/{date}") {
            try {
                val country = call.parameters["country"]!!.toString()
                val month = call.parameters["month"]!!.toInt()
                val date = call.parameters["date"]!!.toInt()
                if (month > 12 || month <= 0 || date > 31 || date <= 0)
                    throw java.lang.Exception("Invalid input")

                val requiredData: CountryData? = getRequiredData(date, month)

                if (requiredData == null) {
                    call.respond("Invalid request")
                } else {
                    call.respond("Date: " + requiredData.getDate().toString() +
                                "\nConfirmed: " + requiredData.getConfirmed().toString() +
                                "\nRecovered: " + requiredData.getRecovered().toString() +
                                "\nDeaths: " + requiredData.getDeaths().toString());
                }
            } catch(e: Exception) {
                call.respond(HttpStatusCode.BadRequest)
            }
        }
    }
}

fun main() {
    getJsonFromFile("./src/main/resources/data.json")
    embeddedServer(Netty, port = 8012, module = Application::dataGetter).start(wait = true)
    /*ApiData.apiData( object :ApiData.Response{
        override fun data(data: Model.Result, status: Boolean) {
            if(status){
                val items:List<Model.Children> = data.data.children
            }
        }

    })*/
}

fun getJsonFromFile(file: String): CovidData {
   /* val mapper = jacksonObjectMapper()
    mapper.registerKotlinModule()*/
    //mapper.registerModule(JavaTimeModule())
    val jsonString: String = File(file).readText(Charsets.UTF_8)
    println(jsonString);
    data = jsonString.toCovidData();
    //println(data.countryInfo)
    return data as CovidData
}

fun getRequiredData(date: Int, month: Int): CountryData? {
    val countryData: List<CountryData?>? = data?.getUS();
    if (countryData != null) {
        for (country_data: CountryData? in countryData) {
            if (country_data != null) {
                if (country_data.getDate().equals("2020-" + month.toString() + "-" + date.toString())) {
                    return country_data
                }
            }
        }
    }
    return null;
}
