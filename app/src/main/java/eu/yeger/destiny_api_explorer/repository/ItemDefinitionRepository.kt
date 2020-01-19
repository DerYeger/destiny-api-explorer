package eu.yeger.destiny_api_explorer.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import eu.yeger.destiny_api_explorer.database.DatabaseItemDefinition
import eu.yeger.destiny_api_explorer.database.ItemDefinitionDatabase
import eu.yeger.destiny_api_explorer.domain.DisplayProperties
import eu.yeger.destiny_api_explorer.domain.ItemDefinition
import eu.yeger.destiny_api_explorer.network.NetworkService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

class ItemDefinitionRepository(private val database: ItemDefinitionDatabase) {

    val items: LiveData<List<ItemDefinition>> =
        Transformations.map(database.itemDefinitionDao.getItemDefinitions()) {
            it.asDomainModel()
        }

    val xurItems: LiveData<List<ItemDefinition>> =
        Transformations.map(database.itemDefinitionDao.getItemDefinitions()) {
            it.filter { item -> item.soldByXur }.asDomainModel()
        }

    suspend fun clear() {
        withContext(Dispatchers.IO) {
            database.itemDefinitionDao.deleteItemDefinitions()
        }
    }

    suspend fun refreshXurItems() {
        withContext(Dispatchers.IO) {
            // Get current Xur data
            val xur = NetworkService.bungieApi.getXur()

            // Map Xur data to sale items
            val saleItems =
                xur.response.sales.data.values.flatMap { vendor -> vendor.saleItems.values }

            // Map sale items to ItemDefinitions by retrieving or fetching them
            val xurItemDefinitions = saleItems.mapNotNull { saleItem ->
                getItem(
                    hash = saleItem.itemHash,
                    soldByXur = true
                )
            }

            // Mark items no longer sold by Xur as such
            val previousItems = xurItems.value!!
                .filter { it !in xurItemDefinitions }
                .asDatabaseModel(soldByXur = false)
            database.itemDefinitionDao.updateAll(*previousItems)

            // Mark items in the inventory of Xur as such
            database.itemDefinitionDao.updateAll(*xurItemDefinitions.asDatabaseModel(soldByXur = true))
        }
    }

    private suspend fun getItem(hash: Long, soldByXur: Boolean = false): ItemDefinition? {
        try {
            // Check if the item definition exists in the local database
            return when (val itemDefinition = database.itemDefinitionDao.getItemDefinition(hash)) {
                // Fetch and save the item definition if it is not present
                null -> NetworkService.bungieApi.getItemDefinition(hash).itemDefinition.also {
                    database.itemDefinitionDao.insertAll(it.asDatabaseModel(soldByXur = soldByXur))
                }
                // Retrieve the item definition if it is present
                else -> itemDefinition.asDomainModel()
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
        iconUrl = this.iconUrl,
        hasIcon = this.hasIcon
    )
)

private fun List<ItemDefinition>.asDatabaseModel(soldByXur: Boolean): Array<DatabaseItemDefinition> =
    map { it.asDatabaseModel(soldByXur = soldByXur) }.toTypedArray()

private fun ItemDefinition.asDatabaseModel(soldByXur: Boolean): DatabaseItemDefinition =
    DatabaseItemDefinition(
        hash = this.hash,
        name = this.displayProperties.name,
        description = this.displayProperties.description,
        iconUrl = this.displayProperties.iconUrl,
        hasIcon = this.displayProperties.hasIcon,
        soldByXur = soldByXur
    )
