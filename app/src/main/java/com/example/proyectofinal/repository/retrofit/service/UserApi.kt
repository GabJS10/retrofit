package com.example.proyectofinal.repository.retrofit.service


import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import com.example.proyectofinal.repository.retrofit.responses.UserResponses
import com.example.proyectofinal.repository.room.entity.User
import retrofit2.http.Path

interface UserApi {

    //login
    @Headers("Content-Type: application/json")
    @POST("users/login/")
    fun login(@Body params: Map<String, String>): Call<UserResponses>

    //register
    @Headers("Content-Type: application/json")
    @POST("users/")
    fun register(@Body params: Map<String, String>): Call<UserResponses>


    //getOne
    @GET("users/{email}/")
    fun getOne(@Path("email") email: String): Call<UserResponses>

}