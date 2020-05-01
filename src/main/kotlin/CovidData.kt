import com.fasterxml.jackson.annotation.JsonAnyGetter
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyOrder
import java.util.HashMap

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder("US")
public class CovidData {
    @JsonProperty("US")
    private val uS: List<CountryData>? = null

    @JsonIgnore
    private val additionalProperties: Map<String, Any> =
        HashMap()

    @JsonProperty("US")
    fun getUS(): List<CountryData?>? {
        return uS
    }

    @JsonAnyGetter
    fun getAdditionalProperties(): Map<String, Any> {
        return additionalProperties
    }
}