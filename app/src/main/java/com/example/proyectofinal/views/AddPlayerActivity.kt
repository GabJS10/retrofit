package com.example.proyectofinal.views

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.proyectofinal.R
import com.example.proyectofinal.viewmodels.PlayerViewModel

class AddPlayerActivity : AppCompatActivity() {

    private lateinit var viewModel: PlayerViewModel

    private lateinit var name: EditText
    private lateinit var age: EditText
    private lateinit var position: EditText
    private lateinit var number: EditText
    private lateinit var image: EditText
    private lateinit var btnAdd: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_player)

        viewModel = ViewModelProvider(this).get(PlayerViewModel::class.java)
        initComponents()
        initListeners()
    }


    private fun initComponents(){
        name = findViewById(R.id.et_name_player)
        age = findViewById(R.id.et_age_player)
        position = findViewById(R.id.et_position_player)
        number = findViewById(R.id.et_number_player)
        image = findViewById(R.id.et_image_player)
        btnAdd = findViewById(R.id.btn_register_player)
    }

    private fun initListeners(){
        btnAdd.setOnClickListener {
            val name = name.text.toString()
            val age = age.text.toString()
            val position = position.text.toString()
            val number = number.text.toString()
            val image = image.text.toString()

            if(name.isNotEmpty() && age.isNotEmpty() && position.isNotEmpty() && number.isNotEmpty()){

                viewModel.insertPlayer(name, age, position, number, image,teamId=intent.getIntExtra("id", 0))
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)


            }
        }

    }
}