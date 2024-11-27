package com.example.proyectofinal.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.proyectofinal.repository.room.repositories.UserRepository
import com.example.proyectofinal.repository.room.database.MyDatabase
import com.example.proyectofinal.repository.room.entity.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

    class UserViewModel(application: Application): AndroidViewModel(application) {
    private val repository: UserRepository
    val allUsers : LiveData<List<User>>

    init {
        val userDao = MyDatabase.getDatabaseClient(application).userDao()
        repository = UserRepository(userDao)
        allUsers = repository.allUsers
    }

    fun getUser(email: String, password: String): LiveData<User> {
        return repository.getUser(email, password)
    }



    //with retrofit

    fun login(email: String, password: String): LiveData<User?> {
        return repository.login(email, password)
    }


    //register
    fun register(email: String, password: String)= viewModelScope.launch(Dispatchers.IO) {
        Log.d("Register", "register: $email $password")
        repository.register(email, password)
    }



}