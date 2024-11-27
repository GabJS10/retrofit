package com.example.proyectofinal.views

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.proyectofinal.R
import com.example.proyectofinal.viewmodels.PlayerViewModel

class UpdatePlayerActivity : AppCompatActivity() {

    private lateinit var viewModel: PlayerViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_update_player)

        // Obtener datos del Intent
        val playerId = intent.getIntExtra("id", -1)
        val playerName = intent.getStringExtra("name")
        val playerAge = intent.getStringExtra("age")
        val playerPosition = intent.getStringExtra("position")
        val playerNumber = intent.getStringExtra("number")
        val playerImage = intent.getStringExtra("image")

        // Vincular vistas
        val etName = findViewById<EditText>(R.id.et_name_player_update)
        val etAge = findViewById<EditText>(R.id.et_age_player_update)
        val etPosition = findViewById<EditText>(R.id.et_position_player_update)
        val etNumber = findViewById<EditText>(R.id.et_number_player_update)
        val etImage = findViewById<EditText>(R.id.et_image_player_update)
        val btnUpdate = findViewById<Button>(R.id.btn_update_player)
        val tvError = findViewById<TextView>(R.id.tv_error)

        // Establecer valores iniciales
        etName.setText(playerName)
        etAge.setText(playerAge)
        etPosition.setText(playerPosition)
        etNumber.setText(playerNumber)
        etImage.setText(playerImage)

        // Configurar ViewModel
        viewModel = ViewModelProvider(this)[PlayerViewModel::class.java]

        // Manejar el clic en el botón de actualizar
        btnUpdate.setOnClickListener {
            val name = etName.text.toString()
            val age = etAge.text.toString()
            val position = etPosition.text.toString()
            val number = etNumber.text.toString()
            val image = etImage.text.toString()

            if (playerId != -1) {
                viewModel.updatePlayer(playerId, name, age, position, number, image)
                    .observe(this) { player ->
                        if (player != null) {
                            Toast.makeText(this, "Jugador actualizado correctamente", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                        } else {
                            tvError.text = "Error al actualizar el jugador."
                        }
                    }
            } else {
                tvError.text = "Error: ID inválido."
            }
        }
    }
}