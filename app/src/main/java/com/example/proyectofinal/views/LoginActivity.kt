package com.example.proyectofinal.views

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.proyectofinal.R
import com.example.proyectofinal.viewmodels.UserViewModel

class LoginActivity : AppCompatActivity() {

    private lateinit var btnRegister: Button
    private lateinit var btnLogin: Button
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var tvError: TextView

    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        initComponent()
        initListeners()

    }


    private fun initComponent() {
        btnRegister = findViewById(R.id.btn_register)
        btnLogin = findViewById(R.id.btn_login)
        etEmail = findViewById(R.id.et_email)
        etPassword = findViewById(R.id.et_password)
        tvError = findViewById(R.id.tv_error)

    }

    private fun initListeners() {
        btnRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
        btnLogin.setOnClickListener {
            onPressedLogin()
        }
    }

    private fun onPressedLogin() {
        var strEmail = etEmail.text.toString().trim()
        var strPassword = etPassword.text.toString().trim()

        if (strEmail.isEmpty() || strPassword.isEmpty()) {
            tvError.text = "Email and password are required"
            return
        }

        userViewModel.login(strEmail, strPassword).observe(this) { user ->
            if ( user != null) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            } else {
                tvError.text = "Invalid email or password"
            }
        }
    }
}