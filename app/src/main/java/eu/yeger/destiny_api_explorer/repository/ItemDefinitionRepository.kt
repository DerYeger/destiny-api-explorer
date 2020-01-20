package eu.yeger.destiny_api_explorer.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import eu.yeger.destiny_api_explorer.database.DatabaseItemDefinition
import eu.yeger.destiny_api_explorer.database.DatabaseSaleItem
import eu.yeger.destiny_api_explorer.database.ItemDefinitionDatabase
import eu.yeger.destiny_api_explorer.domain.DisplayProperties
import eu.yeger.destiny_api_explorer.domain.ItemDefinition
import eu.yeger.destiny_api_explorer.domain.SaleItem
import eu.yeger.destiny_api_explorer.network.NetworkService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

class ItemDefinitionRepository(private val database: ItemDefinitionDatabase) {

    val items: LiveData<List<ItemDefinition>> by lazy {
        Transformations.map(database.itemDefinitions.getAll())
        {
            it.asDomainModel()
        }
    }

    val xurItems: LiveData<List<ItemDefinition>> by lazy {
        Transformations.map(database.saleItems.getItemDefinitions()) {
            it.asDomainModel()
        }
    }

    suspend fun clear() {
        withContext(Dispatchers.IO) {
            database.itemDefinitions.deleteAll()
        }
    }

    suspend fun refreshXurItems() {
        withContext(Dispatchers.IO) {
            val xur = NetworkService.bungieApi.getXur()

            val saleItems = xur.response.sales.data.values
                .flatMap { vendor -> vendor.saleItems.values }
                .map { it.asDatabaseModel() }

            database.saleItems.apply {
                deleteAll()
                insert(*saleItems.toTypedArray())
            }

            saleItems.forEach { getItem(it.itemHash) }
        }
    }

    suspend fun getItem(hash: Long): ItemDefinition? {
        try {
            return withContext(Dispatchers.IO) {
                when (val itemDefinition = database.itemDefinitions.get(hash)) {
                    null -> NetworkService.bungieApi.getItemDefinition(hash).itemDefinition.also {
                        database.itemDefinitions.insert(it.asDatabaseModel())
                    }
                    else -> itemDefinition.asDomainModel()
                }
            }
        } catch (exception: Exception) {
            Timber.e(exception)
        }
        return null
    }
}

private fun List<DatabaseItemDefinition>.asDomainModel(): List<ItemDefinition> =
    map { it.asDomainModel() }

private fun DatabaseItemDefinition.asDomainModel(): ItemDefinition = ItemDefinition(
    hash = this.hash,
    displayProperties = DisplayProperties(
        name = this.name,
        description = this.description,
        iconUrl = this.iconUrl
    ),
    type = this.type,
    screenshotUrl = this.screenshotUrl
)

private fun ItemDefinition.asDatabaseModel(): DatabaseItemDefinition =
    DatabaseItemDefinition(
        hash = this.hash,
        name = this.displayProperties.name,
        description = this.displayProperties.description,
        iconUrl = this.displayProperties.iconUrl,
        type = this.type,
        screenshotUrl = this.screenshotUrl
    )

private fun SaleItem.asDatabaseModel(): DatabaseSaleItem = DatabaseSaleItem(
    itemHash = this.itemHash,
    vendorItemIndex = this.vendorItemIndex,
    quantity = this.quantity
)
