package com.example.proyectofinal.views

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.proyectofinal.R
import com.example.proyectofinal.repository.room.entity.User
import com.example.proyectofinal.viewmodels.UserViewModel

class RegisterActivity : AppCompatActivity() {
    private lateinit var btnRegister: Button
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var tvError: TextView

    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)

        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        initComponent()
        initListeners()


    }

    private fun initComponent() {
        btnRegister = findViewById(R.id.btn_register)
        etEmail = findViewById(R.id.et_email)
        etPassword = findViewById(R.id.et_password)
        tvError = findViewById(R.id.tv_error)

    }

    private fun initListeners() {
        btnRegister.setOnClickListener {
            var strEmail = etEmail.text.toString().trim()
            var strPassword = etPassword.text.toString().trim()


            if (strEmail.isEmpty() || strPassword.isEmpty()) {
                tvError.text = "Por favor, llene todos los campos"
                return@setOnClickListener
            }

            userViewModel.register(email = strEmail, password = strPassword)

            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)

        }
    }
}