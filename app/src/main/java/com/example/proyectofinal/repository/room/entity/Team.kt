package com.example.proyectofinal.repository.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "teams")
data class Team(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "coach")
    val coach: String,

    @ColumnInfo(name = "city")
    val city: String,

    @ColumnInfo(name = "found")
    val found: String,

    @ColumnInfo(name = "image")
    val image: String?,

    @ColumnInfo(name = "userId")
    val userId: Int,

    )