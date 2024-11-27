package com.example.proyectofinal.repository.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.proyectofinal.repository.room.entity.Team


@Dao
interface TeamDao {
    @Query("SELECT * FROM teams")
    fun getAll(): LiveData<List<Team>>

    //register
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(team: Team)


    //insert all teams
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(teams: List<Team>)

    @Query("DELETE FROM teams")
    suspend fun deleteAll()

    //delete
    @Query("DELETE FROM teams WHERE id = :id")
    suspend fun delete(id: Int)

    //update
    @Update()
    suspend fun update(team: Team)


}