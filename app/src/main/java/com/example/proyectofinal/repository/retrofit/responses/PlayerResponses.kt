package com.example.proyectofinal.repository.retrofit.responses

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class PlayerResponses (

    @SerializedName("id")
    @Expose
    val id: Int,

    @SerializedName("name")
    @Expose
    val name: String,


    @SerializedName("age")
    @Expose
    val age: String,


    @SerializedName("position")
    @Expose
    val position: String,


    @SerializedName("number")
    @Expose
    val number: String,


    @SerializedName("image")
    @Expose
    val image: String,


    @SerializedName("teamId")
    @Expose
    val teamId: Int






)