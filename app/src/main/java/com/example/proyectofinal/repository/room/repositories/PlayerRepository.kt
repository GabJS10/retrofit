package com.example.proyectofinal.repository.room.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.proyectofinal.repository.retrofit.apiclient.RestrofitService
import com.example.proyectofinal.repository.retrofit.responses.PlayerResponses
import com.example.proyectofinal.repository.retrofit.service.PlayerApi
import com.example.proyectofinal.repository.room.dao.PlayerDao
import com.example.proyectofinal.repository.room.entity.Player
import com.example.proyectofinal.repository.room.entity.PlayerUpdateRequest
import com.example.proyectofinal.repository.room.entity.Team
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PlayerRepository(private val playerDao: PlayerDao) {
    val playerApi = RestrofitService.getInstance().create(PlayerApi::class.java)

    val viewModelScope = CoroutineScope(Dispatchers.Main)


    fun updatePlayer(id: Int, name: String, age: String, position: String, number: String, image: String): MutableLiveData<Player?> {
        val updatedPlayer = MutableLiveData<Player?>()
        val playerUpdateRequest = PlayerUpdateRequest(
            name = name,
            age = age.toInt(),
            position = position,
            number = number,
            image = image
        )
        Log.d("UpdatePlayer", "ID del jugador: $id")

        playerApi.updatePlayer(id, playerUpdateRequest).enqueue(object : Callback<PlayerResponses> {
            override fun onResponse(
                call: Call<PlayerResponses>,
                response: Response<PlayerResponses>
            ) {
                Log.d("UpdatePlayer", "Respuesta del servidor: ${response.code()} - ${response.message()}")
                if (response.isSuccessful) {
                    val p = Player(
                        response.body()!!.id,
                        response.body()!!.name,
                        response.body()!!.age,
                        response.body()!!.position,
                        response.body()!!.number,
                        response.body()!!.image,
                        response.body()!!.teamId
                    )
                    viewModelScope.launch(Dispatchers.IO) {
                        playerDao.update(p)
                    }
                    updatedPlayer.postValue(p)
                } else {
                    Log.d("Error", "Error al actualizar jugador: ${response.message()}")
                    updatedPlayer.postValue(null)
                }
            }

            override fun onFailure(call: Call<PlayerResponses>, t: Throwable) {
                Log.d("Error", "Error de red al actualizar jugador: ${t.message}")
                updatedPlayer.postValue(null)
            }
        })
        return updatedPlayer
    }


    //delete
    suspend fun deletePlayer(id: Int) {
        try {
            val response = playerApi.deletePlayer(id).execute()
            if (!response.isSuccessful) {
                throw Exception("Error al eliminar el jugador: ${response.code()}")
            }
            viewModelScope.launch(Dispatchers.IO) {
                playerDao.delete(id)
            }
        } catch (e: Exception) {
            throw Exception("Error de red al intentar eliminar el jugador: ${e.message}")
        }
    }

    //insert a player
    fun insertPlayer(name: String, age: String, position: String, number: String, image: String, teamId: Int): MutableLiveData<Player?> {
        val player = MutableLiveData<Player?>()
        val params = mapOf(
            "name" to name,
            "age" to age.toInt(),
            "position" to position,
            "number" to number,
            "image" to image,
            "teamId" to teamId.toString()
        )

        playerApi.insertPlayer(params as Map<String, String>).enqueue(object : Callback<PlayerResponses> {
            override fun onResponse(
                call: Call<PlayerResponses>,
                response: Response<PlayerResponses>
            ) {
                if (response.isSuccessful) {
                    val p = Player(

                        response.body()!!.id,
                        response.body()!!.name,
                        response.body()!!.age,
                        response.body()!!.position,
                        response.body()!!.number,
                        response.body()!!.image,
                        response.body()!!.teamId
                    )
                    viewModelScope.launch(Dispatchers.IO) {
                        playerDao.insert(p)
                    }
                    player.postValue(p)
                }
                if (!response.isSuccessful) {
                    Log.d("error", response.message())
                }
            }

            override fun onFailure(call: Call<PlayerResponses>, t: Throwable) {
                Log.d("error", t.message.toString())
                player.postValue(null)


            }


        })

        return player


}

}