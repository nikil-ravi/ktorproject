import com.sun.xml.internal.ws.client.ContentNegotiation
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
import kotlin.coroutines.Continuation

fun hello(): String {
    return "Hello, world!"
}

data class Result(val operation: String, val first: Int, val second: Int, val result: Int)

fun Application.adder() {
    install(io.ktor.features.ContentNegotiation) {
        gson {}
    }
    routing {
        get("/") {
            call.respondText(hello())
        }
        get("/{operation}/{first}/{second}") {
            try {
                val first = call.parameters["first"]!!.toInt()
                val second = call.parameters["second"]!!.toInt()
                val operation = call.parameters["operation"]!!
                val result = Result(operation, first, second, when (operation) {
                    "add" -> first + second
                    "subtract" -> first - second
                    "multiply" -> first * second
                    else -> throw java.lang.Exception("$operation is not defined")
                })
                call.respond(result)
            } catch(e: Exception) {
                call.respond(HttpStatusCode.BadRequest)
            }
        }
    }
}

fun main() {
    embeddedServer(Netty, port = 8012, module = Application::adder).start(wait = true)
}
