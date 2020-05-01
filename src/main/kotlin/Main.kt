import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.gson.gson
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.routing
import java.io.File
import java.util.HashMap


val data: CovidData? = null;

fun hello(): String {
    return "Hello, world!"
}

data class Case(val date: String, val confirmed: Int, val deaths: Int, val recovered: Int)



fun CovidData.toJson(): String = ObjectMapper().writeValueAsString(this)

fun String.toCovidData(): CovidData = ObjectMapper().readValue(this)

fun Application.adder() {
    install(io.ktor.features.ContentNegotiation) {
        gson {}
    }
    routing {
        get("/") {
            call.respondText("In the URL, specify the following: \n" +
                "1. Country\n" +
                "2. Month (1-12)\n" +
                "3. day of the month (01-30)")
        }
        get("/{country}/{month}/{date}") {
            try {
                val country = call.parameters["country"]!!.toString()
                val month = call.parameters["month"]!!.toInt()
                val date = call.parameters["date"]!!.toInt()
                /*val result = Result(country, month, date, when (operation) {

                    else -> throw java.lang.Exception("Invalid input")
                })*/
                call.respond("Heyo")
            } catch(e: Exception) {
                call.respond(HttpStatusCode.BadRequest)
            }
        }
    }
}

fun main() {
    //embeddedServer(Netty, port = 8012, module = Application::adder).start(wait = true)
    /*ApiData.apiData( object :ApiData.Response{
        override fun data(data: Model.Result, status: Boolean) {
            if(status){
                val items:List<Model.Children> = data.data.children
            }
        }

    })*/
    getJsonFromFile("./src/main/resources/data.json")

}

fun getJsonFromFile(file: String): CovidData {
   /* val mapper = jacksonObjectMapper()
    mapper.registerKotlinModule()*/
    //mapper.registerModule(JavaTimeModule())
    val jsonString: String = File(file).readText(Charsets.UTF_8)
    println(jsonString);
    val data: CovidData = jsonString.toCovidData();
    //println(data.countryInfo)
    return data
}

fun getRequiredData(date: String, month: Int): CountryData {
    val countryData: List<CountryData?>? = data?.getUS();
    if (countryData != null) {
        for (country_data: CountryData? in countryData) {
            if (country_data != null) {
                if (country_data.getDate() == "2020-" + month.toString() + "-" + date.toString()) {

                }
            }
        }
        /*val jsonTextList:List<Covid> = mapper.readValue<List<Covid>>(jsonString)
    for (covidData in jsonTextList) {
        println(covidData)
    }*/

    }
}
