package com.example.proyectofinal.repository.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.proyectofinal.repository.room.entity.Player

@Dao
interface PlayerDao {


    //Insert a player
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(player: Player)

    //Insert a list of players
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(players: List<Player>)

    //Update a player
    @Update(onConflict = OnConflictStrategy.IGNORE)
    suspend fun update(player: Player)


    //delete
    @Query("DELETE FROM player WHERE id = :id")
    suspend fun delete(id: Int)
}