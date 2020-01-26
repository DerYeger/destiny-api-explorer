package eu.yeger.destiny_api_explorer.ui.item_overview

import android.app.Application
import androidx.lifecycle.*
import eu.yeger.destiny_api_explorer.repository.ItemDefinitionRepository
import kotlinx.coroutines.launch

class ItemOverviewViewModel(application: Application) : ViewModel() {

    private val repository = ItemDefinitionRepository(application)

    val itemDefinitions = repository.items

    val isEmpty = Transformations.map(itemDefinitions) {
        it.isEmpty()
    }

    private val _refreshing = MutableLiveData<Boolean>()
    val refreshing: LiveData<Boolean>
        get() = _refreshing

    fun refresh() {
        viewModelScope.launch {
            _refreshing.value = true
            repository.fetchSomeItems()
            _refreshing.value = false
        }
    }

    class Factory(private val application: Application) : ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ItemOverviewViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return ItemOverviewViewModel(application) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}
