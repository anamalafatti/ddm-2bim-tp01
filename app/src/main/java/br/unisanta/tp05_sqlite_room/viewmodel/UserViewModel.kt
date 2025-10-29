package br.unisanta.tp05_sqlite_room.viewmodel

import android.app.Application
import androidx.lifecycle.*
import br.unisanta.tp05_sqlite_room.data.AppDatabase
import br.unisanta.tp05_sqlite_room.data.model.User
import br.unisanta.tp05_sqlite_room.data.repository.UserRepository
import kotlinx.coroutines.launch


class UserViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: UserRepository
    val allUsers: LiveData<List<User>>


    init {
        val userDao = AppDatabase.getDatabase(application).userDao()
        repository = UserRepository(userDao)
        allUsers = repository.getAllLive()
    }


    fun insert(user: User, onResult: (Long) -> Unit) = viewModelScope.launch {
        val id = repository.insert(user)
        onResult(id)
    }


    fun update(user: User) = viewModelScope.launch { repository.update(user) }


    suspend fun getByEmail(email: String) = repository.getByEmail(email)


    suspend fun getById(id: Int) = repository.getById(id)
}


class UserViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return UserViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}