package eu.yeger.destiny_api_explorer.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class NetworkResponse(
    @Json(name = "Response")
    val response: Any
)
