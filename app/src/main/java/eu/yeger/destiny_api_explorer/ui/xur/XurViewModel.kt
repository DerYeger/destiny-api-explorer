package eu.yeger.destiny_api_explorer.ui.xur

import android.app.Application
import androidx.lifecycle.*
import eu.yeger.destiny_api_explorer.database.getDatabase
import eu.yeger.destiny_api_explorer.domain.ItemDefinition
import eu.yeger.destiny_api_explorer.repository.ItemDefinitionRepository
import eu.yeger.destiny_api_explorer.ui.OnClickListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class XurViewModel(application: Application) : AndroidViewModel(application) {

    private val viewModelJob = Job()
    private val viewModelScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val database = getDatabase(application)
    private val repository = ItemDefinitionRepository(database)

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
            repository.clear()
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
