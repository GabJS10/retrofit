package com.example.proyectofinal.repository.room.entity

data class PlayerUpdateRequest(
    val name: String,
    val age: Int,
    val position: String,
    val number: String,
    val image: String
)