package com.example.proyectofinal.repository.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "player")
data class Player (

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,


    @ColumnInfo(name = "name")
    var name: String = "",


    @ColumnInfo(name = "age")
    var age: String = "",

    @ColumnInfo(name = "position")
    var position: String = "",

    @ColumnInfo(name = "number")
    var number: String = "",

    @ColumnInfo(name = "image")
    var image: String? = "",

    @ColumnInfo(name = "team_id")
    var team_id: Int = 0


)