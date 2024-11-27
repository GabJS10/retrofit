package com.example.proyectofinal.repository.retrofit.service

import com.example.proyectofinal.repository.retrofit.responses.TeamResponses
import com.example.proyectofinal.repository.retrofit.responses.TeamWithPlayersResponses
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface TeamApi {


    //getAllTeams
    @Headers("Content-Type: application/json")
    @GET("teams/")
    fun getAllTeams(): Call<List<TeamResponses>>


    //update
    @Headers("Content-Type: application/json")
    @PATCH("teams/{id}/")
    fun updateTeam(@Path("id") id: Int, @Body params: Map<String, String>): Call<TeamResponses>

    //delete
    @Headers("Content-Type: application/json")
    @DELETE("teams/{id}/")
    fun deleteTeam(@Path("id") id: Int): Call<TeamResponses>

    //insertTeam
    @Headers("Content-Type: application/json")
    @POST("teams/")
    fun insertTeam(@Body params: Map<String, String>): Call<TeamResponses>


    //get team with players
    @Headers("Content-Type: application/json")
    @GET("teams/with-players/{id}")
    fun getTeamWithPlayers(@Path("id") id: Int): Call<TeamWithPlayersResponses>

}