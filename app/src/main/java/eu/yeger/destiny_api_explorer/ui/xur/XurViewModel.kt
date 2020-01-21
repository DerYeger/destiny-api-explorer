package eu.yeger.destiny_api_explorer.ui.xur

import android.app.Application
import androidx.lifecycle.*
import eu.yeger.destiny_api_explorer.repository.ItemDefinitionRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class XurViewModel(application: Application) : AndroidViewModel(application) {

    private val viewModelJob = Job()
    private val viewModelScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val repository = ItemDefinitionRepository(application)

    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

    val itemDefinitions = repository.xurItems

    init {
        refresh()
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun refresh() {
        viewModelScope.launch {
            repository.refreshXurItems()
        }
    }

    fun clear() {
        viewModelScope.launch {
            repository.clearXur()
        }
    }

    class Factory(private val application: Application) : ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(XurViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return XurViewModel(application) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}
