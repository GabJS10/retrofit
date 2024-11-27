package com.example.proyectofinal.repository.retrofit.service

import com.example.proyectofinal.repository.retrofit.responses.PlayerResponses
import com.example.proyectofinal.repository.room.entity.PlayerUpdateRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Headers
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface PlayerApi {

    //insert
    @Headers("Content-Type: application/json")
    @POST("players/")
    fun insertPlayer(@Body params: Map<String, String>): Call<PlayerResponses>


    //update
    @Headers("Content-Type: application/json")
    @PATCH("players/{id}")
    fun updatePlayer(@Path("id") id: Int,@Body playerUpdateRequest: PlayerUpdateRequest): Call<PlayerResponses>


    //delete
    @Headers("Content-Type: application/json")
    @DELETE("players/{id}")
    fun deletePlayer(@Path("id") id: Int): Call<PlayerResponses>

}