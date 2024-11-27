package com.example.proyectofinal.views

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.proyectofinal.R
import com.example.proyectofinal.viewmodels.TeamViewModel

class UpdateTeamActivity : AppCompatActivity() {

    private lateinit var viewModel: TeamViewModel

    private lateinit var name: EditText
    private lateinit var coach: EditText
    private lateinit var city: EditText
    private lateinit var found: EditText
    private lateinit var image: EditText
    private lateinit var btnUpdate: Button

    private var id: Int = 0
    private var nameTeam: String = ""
    private var coachTeam: String = ""
    private var cityTeam: String = ""
    private var foundTeam: String = ""
    private var imageTeam: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_update_team)

        viewModel = TeamViewModel(application)

        initComponents()

        getIntentData()

        fillData()

        btnUpdate.setOnClickListener {
            updateTeam()
        }
    }

    private fun initComponents() {
        name = findViewById(R.id.et_name_team_update)
        coach = findViewById(R.id.et_coach_update)
        city = findViewById(R.id.et_city_update)
        found = findViewById(R.id.et_found_update)
        image = findViewById(R.id.et_image_update)
        btnUpdate = findViewById(R.id.btn_update_team)
    }

    private fun getIntentData() {
        id = intent.getIntExtra("id", 0)
        nameTeam = intent.getStringExtra("name") ?: ""
        coachTeam = intent.getStringExtra("coach") ?: ""
        cityTeam = intent.getStringExtra("city") ?: ""
        foundTeam = intent.getStringExtra("found") ?: ""
        imageTeam = intent.getStringExtra("image") ?: ""
    }

    private fun fillData() {
        name.setText(nameTeam)
        coach.setText(coachTeam)
        city.setText(cityTeam)
        found.setText(foundTeam)
        image.setText(imageTeam)
    }

    private fun updateTeam() {
        val updatedName = name.text.toString()
        val updatedCoach = coach.text.toString()
        val updatedCity = city.text.toString()
        val updatedFound = found.text.toString()
        val updatedImage = image.text.toString()

        if (updatedName.isBlank() || updatedCoach.isBlank() || updatedCity.isBlank() || updatedFound.isBlank()) {
            Toast.makeText(this, "Todos los campos deben estar llenos", Toast.LENGTH_SHORT).show()
            return
        }

        viewModel.updateTeam(id, updatedName, updatedCoach, updatedCity, updatedFound, updatedImage)
            .observe(this) { success ->
                if (success == true) {
                    Toast.makeText(this, "Equipo actualizado exitosamente", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this, "Error al actualizar el equipo", Toast.LENGTH_SHORT).show()
                }
            }
    }

}