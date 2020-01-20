package eu.yeger.destiny_api_explorer.domain

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class ItemDefinition(
    val hash: Long,
    val displayProperties: DisplayProperties,
    @Json(name = "itemTypeAndTierDisplayName")
    val type: String,
    @Json(name = "screenshot")
    val screenshotUrl: String?
) : Parcelable

@Parcelize
data class DisplayProperties(
    val name: String,
    val description: String,
    @Json(name = "icon")
    val iconUrl: String
) : Parcelable

@JsonClass(generateAdapter = true)
data class SaleItem(
    val itemHash: Long,
    val vendorItemIndex: Long,
    val quantity: Long,
    val costs: List<Cost>
)

@JsonClass(generateAdapter = true)
data class Cost(
    val itemHash: Long,
    val quantity: Long
)
