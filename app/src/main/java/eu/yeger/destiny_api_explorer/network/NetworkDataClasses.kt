package eu.yeger.destiny_api_explorer.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import eu.yeger.destiny_api_explorer.domain.ItemDefinition

typealias SaleItems = Map<String, SaleItem>
typealias Vendors = Map<String, NetworkVendors.Response.Sales.Vendor>

@JsonClass(generateAdapter = true)
data class NetworkVendors(
    @Json(name = "Response")
    val response: Response
) {
    @JsonClass(generateAdapter = true)
    data class Response(
        val sales: Sales
    ) {
        @JsonClass(generateAdapter = true)
        data class Sales(
            val data: Vendors
        ) {
            @JsonClass(generateAdapter = true)
            data class Vendor(
                val saleItems: SaleItems
            )
        }
    }
}

@JsonClass(generateAdapter = true)
data class SaleItem(
    val vendorItemIndex: Long,
    val itemHash: Long,
    val quantity: Long,
    val costs: List<Cost>
) {
    @JsonClass(generateAdapter = true)
    data class Cost(
        val itemHash: Long,
        val quantity: Long
    )
}

@JsonClass(generateAdapter = true)
data class NetworkItemDefinition(
    @Json(name = "Response")
    val itemDefinition: ItemDefinition
)
