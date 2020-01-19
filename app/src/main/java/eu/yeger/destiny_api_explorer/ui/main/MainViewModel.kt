package eu.yeger.destiny_api_explorer.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import eu.yeger.destiny_api_explorer.network.NetworkService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber

class MainViewModel : ViewModel() {

    private val viewModelJob = Job()
    private val viewModelScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    val currentQuery = MutableLiveData<String>()

    private val _queryResult = MutableLiveData<String>().apply { value = "No request made" }
    val queryResult: LiveData<String>
        get() = _queryResult

    fun runCurrentQuery() {
        viewModelScope.launch {
            try {
                Timber.i("Requesting: ${currentQuery.value}")
                _queryResult.value = NetworkService.bungieApi.get(currentQuery.value!!).response.toString()
            } catch (exception: Exception) {
                _queryResult.value = exception.localizedMessage
            }
        }
    }

    fun getXur() {
        viewModelScope.launch {
            _queryResult.value = NetworkService.bungieApi.getXur().response.toString()
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
