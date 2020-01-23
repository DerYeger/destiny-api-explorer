package eu.yeger.destiny_api_explorer.ui.item_detail

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import eu.yeger.destiny_api_explorer.domain.ItemDefinition
import eu.yeger.destiny_api_explorer.repository.ItemDefinitionRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ItemDetailViewModel(
    val itemDefinition: ItemDefinition,
    application: Application
) : ViewModel() {

    private val viewModelJob = Job()
    private val viewModelScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val repository = ItemDefinitionRepository(application)

    private val _navigateBack = MutableLiveData<Boolean>()
    val navigateBack: LiveData<Boolean>
        get() = _navigateBack

    fun removeItemDefinition() {
        viewModelScope.launch {
            repository.removeItem(itemDefinition)
            _navigateBack.value = true
        }
    }

    fun navigatedBack() {
        _navigateBack.value = false
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    class Factory(
        private val itemDefinition: ItemDefinition,
        private val application: Application
    ) : ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ItemDetailViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return ItemDetailViewModel(itemDefinition, application) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}