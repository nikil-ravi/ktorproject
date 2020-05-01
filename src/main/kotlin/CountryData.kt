import com.fasterxml.jackson.annotation.JsonAnyGetter
import com.fasterxml.jackson.annotation.JsonAnySetter
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyOrder
import java.util.HashMap

/**
 * Represents a specific country's data for a specific date-month-year triple.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder(
    "date",
    "confirmed",
    "deaths",
    "recovered"
)
public class CountryData {
    @JsonProperty("date")
    private var date: String? = null

    @JsonProperty("confirmed")
    private var confirmed: Int? = null

    @JsonProperty("deaths")
    private var deaths: Int? = null

    @JsonProperty("recovered")
    private var recovered: Int? = null

    @JsonIgnore
    private val additionalProperties: MutableMap<String, Any> =
        HashMap()

    @JsonProperty("date")
    fun getDate(): String? {
        return date
    }

    @JsonProperty("date")
    fun setDate(date: String?) {
        this.date = date
    }

    @JsonProperty("confirmed")
    fun getConfirmed(): Int? {
        return confirmed
    }

    @JsonProperty("confirmed")
    fun setConfirmed(confirmed: Int?) {
        this.confirmed = confirmed
    }

    @JsonProperty("deaths")
    fun getDeaths(): Int? {
        return deaths
    }

    @JsonProperty("deaths")
    fun setDeaths(deaths: Int?) {
        this.deaths = deaths
    }

    @JsonProperty("recovered")
    fun getRecovered(): Int? {
        return recovered
    }

    @JsonProperty("recovered")
    fun setRecovered(recovered: Int?) {
        this.recovered = recovered
    }

    @JsonAnyGetter
    fun getAdditionalProperties(): Map<String, Any>? {
        return additionalProperties
    }

    @JsonAnySetter
    fun setAdditionalProperty(name: String, value: Any) {
        additionalProperties[name] = value
    }
}