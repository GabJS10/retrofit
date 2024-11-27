package com.example.proyectofinal.repository.retrofit.responses

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class UserResponses (

    @SerializedName("id")
    @Expose
    val id:Int,

    @SerializedName("email")
    @Expose
    val email:String,


    @SerializedName("password")
    @Expose
    val password:String


)