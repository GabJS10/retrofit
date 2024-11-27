package com.example.proyectofinal.repository.retrofit.responses

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class TeamWithPlayersResponses (

    @SerializedName("id")
    @Expose
    val id:Int,

    @SerializedName("name")
    @Expose
    val name:String,


    @SerializedName("coach")
    @Expose
    val coach:String,


    @SerializedName("city")
    @Expose
    val city:String,


    @SerializedName("found")
    @Expose
    val found:String,


    @SerializedName("image")
    @Expose
    val image:String,


    @SerializedName("userId")
    @Expose
    val userId:Int,

    @SerializedName("players")
    @Expose
    val players: List<PlayerResponses>


)