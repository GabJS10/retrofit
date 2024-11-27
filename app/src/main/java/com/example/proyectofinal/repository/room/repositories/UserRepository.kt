package com.example.proyectofinal.repository.room.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.proyectofinal.repository.retrofit.apiclient.RestrofitService
import com.example.proyectofinal.repository.retrofit.responses.UserResponses
import com.example.proyectofinal.repository.retrofit.service.UserApi
import com.example.proyectofinal.repository.room.dao.UserDao
import com.example.proyectofinal.repository.room.entity.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class UserRepository(private val userDao: UserDao) {
    val userApi = RestrofitService.getInstance().create(UserApi::class.java)

    val viewModelScope = CoroutineScope(Dispatchers.Main)

    val allUsers: LiveData<List<User>> = userDao.getAll()

    fun getUser(email: String, password: String): LiveData<User> {
        return userDao.getByEmailAndPassword(email, password)
    }

    //with retrofit
    fun login(email: String, password: String): LiveData<User?> {
        Log.d("Login", "login: $email $password")
        val users = MutableLiveData<User?>()
        val params = mapOf("email" to email, "password" to password)

        userApi.login(params).enqueue(object : Callback<UserResponses> {
            override fun onResponse(call: Call<UserResponses>, response: Response<UserResponses>) {
                Log.d("Login", "onResponse: $response")
                //if status 200
                if (response.isSuccessful) {
                    Log.d("Login", "onResponse: ${response.body()}")
                    val user = User(
                        response.body()!!.id,
                        response.body()!!.email,
                        response.body()!!.password
                    )
                    users.postValue(user)
                }else {
                    users.postValue(null)
                }

            }

            override fun onFailure(call: Call<UserResponses>, t: Throwable) {
                Log.d("Login", "onFailure: ${t.message}")
                users.postValue(null)

            }
        })
    return users
    }

    //register
     fun register(email: String, password: String): LiveData<User?> {
        val users = MutableLiveData<User?>()
        val params = mapOf("email" to email, "password" to password)


        userApi.register(params).enqueue(object : Callback<UserResponses> {
            override fun onResponse(call: Call<UserResponses>, response: Response<UserResponses>) {
                if (response.isSuccessful) {
                    val user = User(
                        response.body()!!.id,
                        response.body()!!.email,
                        response.body()!!.password
                    )
                    viewModelScope.launch(Dispatchers.IO) {
                        userDao.insert(user)
                    }
                    users.postValue(user)

                } else {
                    users.postValue(null)
                }
            }

            override fun onFailure(call: Call<UserResponses>, t: Throwable) {
                Log.d("Register", "onFailure: ${t.message}")
                users.postValue(null)
            }
        })

        return users

    }

}