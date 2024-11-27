package com.example.proyectofinal.views

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectofinal.R
import com.example.proyectofinal.repository.room.entity.TeamWithPlayers
import com.example.proyectofinal.viewmodels.TeamViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.squareup.picasso.Picasso

class DetailTeam : AppCompatActivity() {


    private lateinit var viewModel: TeamViewModel

    private lateinit var name: TextView
    private lateinit var coach: TextView
    private lateinit var city: TextView
    private lateinit var found: TextView
    private lateinit var image: ImageView
    private lateinit var btnAddPlayer: FloatingActionButton
    private lateinit var myRecyclerView: RecyclerView
    val adapter : PlayerAdapter = PlayerAdapter()
    private var id: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detail_team)

        viewModel = ViewModelProvider(this).get(TeamViewModel::class.java)
        id = intent.getIntExtra("id", 0)
        initComponets()
        setUpRecyclerView()
        initListeners()

    }

    private fun initComponets(){
        name = findViewById(R.id.team_name)
        coach = findViewById(R.id.team_coach)
        city = findViewById(R.id.team_city)
        found = findViewById(R.id.team_founded)
        image = findViewById(R.id.team_image)
        btnAddPlayer = findViewById(R.id.btnAdd)
        myRecyclerView = findViewById(R.id.players_recycler_view)
    }

    private fun initListeners(){
        btnAddPlayer.setOnClickListener {
            val intent = Intent(this, AddPlayerActivity::class.java)
            intent.putExtra("id", id)
            startActivity(intent)
        }
    }



    private fun setUpRecyclerView(){

        myRecyclerView.adapter = adapter
        myRecyclerView.layoutManager = LinearLayoutManager(this)

        setUpViewModels(viewModel)
    }

    private fun setUpViewModels(viewModel: TeamViewModel){
        adapter.PlayerAdapter(this)


        viewModel.getTeamWithPlayers(id).observe(this,  object : Observer<TeamWithPlayers?> {
            override fun onChanged(value: TeamWithPlayers?) {

                if (value != null) {
                    name.text = value.team.name
                    coach.text = value.team.coach
                    city.text = value.team.city
                    found.text = value.team.found
                    if(value.team.image != null){
                        image.loadUrl(value.team.image)
                    }
                    adapter.setData(value.players)
                    myRecyclerView.adapter = adapter
                }
            }


        })


    }

    fun ImageView.loadUrl(url: kotlin.String) {
        if (url.isNotEmpty()){
            Picasso.with(context).load(url).into(this)
        }
    }
}