package com.example.proyectofinal.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.proyectofinal.repository.room.database.MyDatabase
import com.example.proyectofinal.repository.room.entity.Player
import com.example.proyectofinal.repository.room.repositories.PlayerRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PlayerViewModel(application: Application): AndroidViewModel(application)  {

    private val repository: PlayerRepository

    init {
        val playerDao = MyDatabase.getDatabaseClient(application).playerDao()
        repository = PlayerRepository(playerDao)
    }

    fun insertPlayer(name: String, age: String, position: String, number: String, image: String, teamId: Int)=viewModelScope.launch(
        Dispatchers.IO)  {
        repository.insertPlayer(name, age, position, number, image, teamId)
    }
    fun updatePlayer(
        id: Int,
        name: String,
        age: String,
        position: String,
        number: String,
        image: String
    ): LiveData<Player?> {
        return repository.updatePlayer(id, name, age, position, number, image)
    }

    fun deletePlayer(id: Int): LiveData<Boolean> {
        val deleteResult = MutableLiveData<Boolean>()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repository.deletePlayer(id)
                deleteResult.postValue(true)
            } catch (e: Exception) {
                deleteResult.postValue(false)
            }
        }
        return deleteResult
    }

}