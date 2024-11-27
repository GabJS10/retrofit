package com.example.proyectofinal.views

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.proyectofinal.R
import com.example.proyectofinal.repository.room.entity.Team
import com.example.proyectofinal.viewmodels.TeamViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    lateinit var viewModel: TeamViewModel
    lateinit var mRecyclerView : RecyclerView
    val adapter : TeamAdapter = TeamAdapter()
    lateinit var swipe: SwipeRefreshLayout
    lateinit var btnAdd: FloatingActionButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this).get(TeamViewModel::class.java)
        swipe = findViewById(R.id.swipeRefreshLayout)

        setUpRecyclerView()

        swipe.setOnRefreshListener({
            setUpRecyclerView()
            swipe.setRefreshing(false)
        })

        initComponents()
        initListeners()

    }

    private fun initComponents(){
        btnAdd = findViewById(R.id.btnAdd)
    }

    private fun initListeners(){
        btnAdd.setOnClickListener{
            val intent = Intent(this, AddTeamActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setUpRecyclerView(){

        mRecyclerView = findViewById(R.id.rcView)
        mRecyclerView.adapter = adapter
        mRecyclerView.layoutManager = LinearLayoutManager(this)

        setUpViewModels(viewModel)
    }

    private fun setUpViewModels(viewModel: TeamViewModel){
            adapter.TeamAdapter(this)

            viewModel.allTeams.observe(this, object : Observer<List<Team>> {
                override fun onChanged(value: List<Team>) {
                    if (value != null) {
                        adapter.setData(value)
                        mRecyclerView.adapter = adapter
                    }
                }
            })
    }
}