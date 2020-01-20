package eu.yeger.destiny_api_explorer.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DatabaseItemDefinition(
    @PrimaryKey
    val hash: Long,
    val name: String,
    val description: String,
    val iconUrl: String,
    val type: String,
    val screenshotUrl: String?
)

@Entity
data class DatabaseSaleItem(
    @PrimaryKey
    val itemHash: Long,
    val vendorItemIndex: Long,
    val quantity: Long
)
