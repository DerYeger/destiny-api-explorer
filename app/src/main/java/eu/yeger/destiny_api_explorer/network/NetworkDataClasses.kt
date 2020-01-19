package eu.yeger.destiny_api_explorer.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NetworkResponse(
    @Json(name = "Response")
    val response: Any
)

@JsonClass(generateAdapter = true)
data class NetworkItemDefinition(
    @Json(name = "Response")
    val itemDefinition: ItemDefinition
)

@JsonClass(generateAdapter = true)
data class ItemDefinition(
    val displayProperties: DisplayProperties
)

data class DisplayProperties(
    val description: String,
    val name: String,
    @Json(name = "icon")
    val iconUrl: String,
    val hasIcon: Boolean
)
