package com.example.proyectofinal.repository.room.database

import android.content.Context
import androidx.room.*
import androidx.room.Database
import com.example.proyectofinal.repository.room.dao.PlayerDao
import com.example.proyectofinal.repository.room.dao.TeamDao
import com.example.proyectofinal.repository.room.dao.UserDao
import com.example.proyectofinal.repository.room.entity.Player
import com.example.proyectofinal.repository.room.entity.Team
import com.example.proyectofinal.repository.room.entity.User


@Database(entities = [User::class, Team::class, Player::class], version = 4, exportSchema = false)
abstract class MyDatabase: RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun teamDao(): TeamDao
    abstract fun playerDao(): PlayerDao

    companion object {

        @Volatile
        private var INSTANCE: MyDatabase? = null

        fun getDatabaseClient(context: Context) : MyDatabase {

            if (INSTANCE != null) return INSTANCE!!

            synchronized(this) {

                INSTANCE = Room
                    .databaseBuilder(context, MyDatabase::class.java, "MYDATABASEPROYECTOFINAL")
                    .fallbackToDestructiveMigration()
                    .build()

                return INSTANCE!!

            }
        }

    }


}