package com.example.proyectofinal.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.proyectofinal.repository.room.database.MyDatabase
import com.example.proyectofinal.repository.room.entity.Team
import com.example.proyectofinal.repository.room.entity.TeamWithPlayers
import com.example.proyectofinal.repository.room.repositories.TeamRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TeamViewModel(application: Application): AndroidViewModel(application) {
    private val repository: TeamRepository
    val allTeams : LiveData<List<Team>>


    init {
        val teamDao = MyDatabase.getDatabaseClient(application).teamDao()
        repository = TeamRepository(teamDao)
        allTeams = repository.getAllTeams
    }


    fun insertTeam(name: String, coach: String, city: String, found: String, image: String, userId: Int)=viewModelScope.launch(Dispatchers.IO)  {
         repository.insertTeam(name, coach, city, found, image, userId)
    }


    fun getTeamWithPlayers(id: Int): LiveData<TeamWithPlayers?> {
        return repository.getTeamWithPlayers(id)
    }

    fun updateTeam(id: Int, name: String, coach: String, city: String, found: String, image: String): LiveData<Boolean> {
        val updateResult = MutableLiveData<Boolean>()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repository.updateTeam(id, name, coach, city, found, image)
                updateResult.postValue(true)
            } catch (e: Exception) {
                updateResult.postValue(false)
            }
        }
        return updateResult
    }

    fun deleteTeam(id: Int): LiveData<Boolean> {
        val deleteResult = MutableLiveData<Boolean>()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repository.deleteTeam(id)
                deleteResult.postValue(true)
            } catch (e: Exception) {
                deleteResult.postValue(false)
            }
        }
        return deleteResult
    }

}