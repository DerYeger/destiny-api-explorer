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

    val itemDefinitions = repository.xurItems

    private val _refreshing = MutableLiveData<Boolean>()
    val refreshing: LiveData<Boolean>
        get() = _refreshing

    val refresh: () -> Unit = {
        viewModelScope.launch {
            _refreshing.value = true
            repository.refreshXurItems()
            _refreshing.value = false
        }
    }

    init {
        refresh()
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
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
