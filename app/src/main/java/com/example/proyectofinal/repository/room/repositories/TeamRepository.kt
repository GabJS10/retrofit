package com.example.proyectofinal.repository.room.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.proyectofinal.repository.retrofit.apiclient.RestrofitService
import com.example.proyectofinal.repository.retrofit.responses.TeamResponses
import com.example.proyectofinal.repository.retrofit.responses.TeamWithPlayersResponses
import com.example.proyectofinal.repository.retrofit.service.TeamApi
import com.example.proyectofinal.repository.room.dao.TeamDao
import com.example.proyectofinal.repository.room.entity.Player
import com.example.proyectofinal.repository.room.entity.Team
import com.example.proyectofinal.repository.room.entity.TeamWithPlayers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
        import retrofit2.Callback
import retrofit2.Response

class TeamRepository(private val teamDao: TeamDao)  {
    val teamApi = RestrofitService.getInstance().create(TeamApi::class.java)

    val viewModelScope = CoroutineScope(Dispatchers.Main)


    val getAllTeams: LiveData<List<Team>> = teamDao.getAll()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getAllTeams()
        }
    }
    //get all teams
    //with retrofit
    fun getAllTeams(): MutableLiveData<List<Team>?> {
        var teams = MutableLiveData<List<Team>?>()
        teamApi.getAllTeams().enqueue(object : Callback<List<TeamResponses>> {
            override fun onResponse(call: Call<List<TeamResponses>>, response: Response<List<TeamResponses>>) {

                if (response.isSuccessful) {
                    teams.postValue(response.body()!!.map {

                        Team(
                            it.id,
                            it.name,
                            it.coach,
                            it.city,
                            it.found,
                            it.image,
                            it.userId
                        )

                    })

                    viewModelScope.launch(Dispatchers.IO) {
                        Log.d("InsertTeam", "insertTeam: ${response.body()}")
                        teamDao.deleteAll()
                        teamDao.insertAll(
                            response.body()!!.map {
                                Team(
                                    it.id,
                                    it.name,
                                    it.coach,
                                    it.city,
                                    it.found,
                                    it.image,
                                    it.userId
                                )
                            }
                        )
                    }
                }else{
                    teams.postValue(null)
                }
            }
            override fun onFailure(call: Call<List<TeamResponses>>, t: Throwable) {
                teams.postValue(null)
            }
        })
        return teams
        }


    //get teams with players
    fun getTeamWithPlayers(id: Int): MutableLiveData<TeamWithPlayers?> {
        val teamWithPlayers = MutableLiveData<TeamWithPlayers?>()


        teamApi.getTeamWithPlayers(id).enqueue(object : Callback<TeamWithPlayersResponses> {
            override fun onResponse(call: Call<TeamWithPlayersResponses>, response: Response<TeamWithPlayersResponses>) {
                if (response.isSuccessful) {
                    val t = TeamWithPlayers(
                        team = Team (
                            response.body()!!.id,
                            response.body()!!.name,
                            response.body()!!.coach,
                            response.body()!!.city,
                            response.body()!!.found,
                            response.body()!!.image,
                            response.body()!!.userId
                        ),
                        players =  response.body()!!.players.map {
                                Player(
                                    it.id,
                                    it.name,
                                    it.age,
                                    it.position,
                                    it.number,
                                    it.image,
                                    it.teamId
                                )
                        }

                    )

                    teamWithPlayers.postValue(t)
                }

            }
            override fun onFailure(call: Call<TeamWithPlayersResponses>, t: Throwable) {
                teamWithPlayers.postValue(null)
            }
        })
        return teamWithPlayers
    }


    //insert team
    fun insertTeam(name: String, coach: String, city: String, found: String, image: String, userId: Int): LiveData<Team?> {
        val team = MutableLiveData<Team?>()

        val params = mapOf(
            "name" to name,
            "coach" to coach,
            "city" to city,
            "found" to found,
            "image" to image,
            "userId" to userId.toString()
        )

        Log.d("InsertTeam", "insertTeam: $params")

        teamApi.insertTeam(params as Map<String, String>).enqueue(object : Callback<TeamResponses> {
            override fun onResponse(call: Call<TeamResponses>, response: Response<TeamResponses>) {
                Log.d("InsertTeam", "onResponse: $response")
                if(response.isSuccessful){
                    val t = Team(
                        response.body()!!.id,
                        response.body()!!.name,
                        response.body()!!.coach,
                        response.body()!!.city,
                        response.body()!!.found,
                        response.body()!!.image,
                        response.body()!!.userId
                    )
                    viewModelScope.launch(Dispatchers.IO) {
                        teamDao.insert(t)
                    }
                    team.postValue(t)
                }

                if (!response.isSuccessful) {
                    Log.e("InsertTeam", "Error: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<TeamResponses>, t: Throwable) {
                Log.d("InsertTeam", "onFailure: ${t.message}")
                team.postValue(null)
            }
        })
        return team


    }

    // Actualizar un equipo
    fun updateTeam(id: Int, name: String, coach: String, city: String, found: String, image: String?): LiveData<Boolean> {
        val result = MutableLiveData<Boolean>()

        val params = mutableMapOf<String, String>(
            "name" to name,
            "coach" to coach,
            "city" to city,
            "found" to found
        )

        if (!image.isNullOrEmpty()) {
            params["image"] = image
        }

        teamApi.updateTeam(id, params).enqueue(object : Callback<TeamResponses> {
            override fun onResponse(call: Call<TeamResponses>, response: Response<TeamResponses>) {
                if (response.isSuccessful) {
                    val updatedTeam = response.body()?.let {
                        Team(
                            it.id,
                            it.name,
                            it.coach,
                            it.city,
                            it.found,
                            it.image,
                            it.userId
                        )
                    }

                    if (updatedTeam != null) {
                        viewModelScope.launch(Dispatchers.IO) {
                            teamDao.update(updatedTeam)
                        }
                        result.postValue(true)
                    } else {
                        result.postValue(false)
                    }
                } else {
                    Log.e("UpdateTeam", "Error: ${response.errorBody()?.string()}")
                    result.postValue(false)
                }
            }

            override fun onFailure(call: Call<TeamResponses>, t: Throwable) {
                Log.e("UpdateTeam", "Failure: ${t.message}")
                result.postValue(false)
            }
        })

        return result
    }

    suspend fun deleteTeam(id: Int) {
        try {
            val response = teamApi.deleteTeam(id).execute()
            if (!response.isSuccessful) {
                throw Exception("Error al eliminar el equipo: ${response.code()}")
            }
            viewModelScope.launch(Dispatchers.IO) {
                teamDao.delete(id)
            }
        } catch (e: Exception) {
            throw Exception("Error de red al intentar eliminar el equipo: ${e.message}")
        }
    }


}


