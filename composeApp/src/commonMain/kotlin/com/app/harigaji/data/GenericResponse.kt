import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class GenericResponse(
    val status: Int,
    val message: String,
    val response: JsonElement? = null
)
