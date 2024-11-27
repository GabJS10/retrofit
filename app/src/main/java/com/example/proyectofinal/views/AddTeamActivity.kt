package com.example.proyectofinal.views

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.proyectofinal.R
import com.example.proyectofinal.viewmodels.TeamViewModel

class AddTeamActivity : AppCompatActivity() {

    private lateinit var viewModel: TeamViewModel
    private lateinit var name: EditText
    private lateinit var coach: EditText
    private lateinit var city: EditText
    private lateinit var found: EditText
    private lateinit var image: EditText
    private lateinit var btnAdd: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_team)
        viewModel = ViewModelProvider(this).get(TeamViewModel::class.java)
        initComponets()
        initListeners()
    }

    private fun initComponets(){
        name = findViewById(R.id.et_name)
        coach = findViewById(R.id.et_coach)
        city = findViewById(R.id.et_city)
        found = findViewById(R.id.et_found)
        image = findViewById(R.id.et_image)
        btnAdd = findViewById(R.id.btn_register)
    }

    private fun initListeners(){
        btnAdd.setOnClickListener{
            val name = name.text.toString()
            val coach = coach.text.toString()
            val city = city.text.toString()
            val found = found.text.toString()
            val image = image.text.toString()

            if(name.isNotEmpty() && coach.isNotEmpty() && city.isNotEmpty() && found.isNotEmpty()){
                viewModel.insertTeam(name, coach, city, found, image,userId=1)
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }

    }
}