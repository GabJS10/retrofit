package com.example.proyectofinal.repository.retrofit.apiclient

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RestrofitService {
    val BASE_URL = "http://10.0.2.2:3000/api/"
    //val URL = "http://172.20.10.2:8888";

    fun getInstance(): Retrofit {
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}