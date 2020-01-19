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

    val xurItems: LiveData<List<ItemDefinition>> =
        Transformations.map(database.itemDefinitionDao.getItemDefinitions()) {
            it.asDomainModel()
        }

    suspend fun refreshXurItems() {
        withContext(Dispatchers.IO) {
            val xur = NetworkService.bungieApi.getXur()
            xur.response.sales.data.values.forEach { vendor ->
                vendor.saleItems.values.forEach { saleItem ->
                    getItem(saleItem.itemHash)
                }
            }
        }
    }

    private suspend fun getItem(hash: Long): ItemDefinition? {
        try {
            return when (val itemDefinition = database.itemDefinitionDao.getItemDefinition(hash)) {
                null -> NetworkService.bungieApi.getItemDefinition(hash).itemDefinition.also {
                    database.itemDefinitionDao.insertAll(it.asDatabaseModel())
                }
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

private fun ItemDefinition.asDatabaseModel(): DatabaseItemDefinition = DatabaseItemDefinition(
    hash = this.hash,
    name = this.displayProperties.name,
    description = this.displayProperties.description,
    iconUrl = this.displayProperties.iconUrl,
    hasIcon = this.displayProperties.hasIcon
)
