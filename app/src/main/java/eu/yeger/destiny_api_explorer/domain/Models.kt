package eu.yeger.destiny_api_explorer.domain

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ItemDefinition(
    val displayProperties: DisplayProperties,
    val hash: Long
)

data class DisplayProperties(
    val name: String,
    val description: String,
    @Json(name = "icon")
    val iconUrl: String,
    val hasIcon: Boolean
)