package eu.yeger.destiny_api_explorer.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import eu.yeger.destiny_api_explorer.network.ItemDefinition
import eu.yeger.destiny_api_explorer.network.NetworkService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber

class MainViewModel : ViewModel() {

    private val viewModelJob = Job()
    private val viewModelScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

    val currentQuery = MutableLiveData<String>()

    private val _itemDefinition = MutableLiveData<ItemDefinition>()
    val itemDefinition: LiveData<ItemDefinition>
        get() = _itemDefinition

    fun runCurrentQuery() {
        viewModelScope.launch {
            try {
                _itemDefinition.value = null
                _error.value = null
                Timber.i("Requesting: ${currentQuery.value}")
                _itemDefinition.value = NetworkService.bungieApi.getItemDefinition(currentQuery.value!!).itemDefinition
                Timber.i(itemDefinition.value.toString())
            } catch (exception: Exception) {
                _itemDefinition.value = null
                _error.value = exception.localizedMessage
            }
        }
    }

    fun getXur() {
        viewModelScope.launch {
            _itemDefinition.value = NetworkService.bungieApi.getXur().itemDefinition
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
