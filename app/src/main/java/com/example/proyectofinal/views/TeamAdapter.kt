package com.example.proyectofinal.views

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectofinal.R
import com.example.proyectofinal.repository.room.entity.Team
import com.example.proyectofinal.viewmodels.TeamViewModel
import com.squareup.picasso.Picasso

class TeamAdapter: RecyclerView.Adapter<TeamAdapter.ViewHolder>() {
    private var teams = emptyList<Team>()
    private lateinit var context: Context

    fun TeamAdapter(context: Context){
        this.context = context
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name = view.findViewById(R.id.tvOne) as TextView
        val coach = view.findViewById(R.id.tvTwo) as TextView
        val city = view.findViewById(R.id.tvThree) as TextView
        val photo = view.findViewById(R.id.ivPhoto) as ImageView
        val btnUpdate = view.findViewById(R.id.btnUpdate) as ImageView
        val btnDelete= view.findViewById(R.id.btnDelete) as ImageView

        fun bind(team: Team, context: Context){


            name.text = team.name
            coach.text = team.coach
            city.text = team.city

            if(team.image != null){
                Log.d("URL", team.image)
                photo.loadUrl(team.image)
            }


            itemView.setOnClickListener {
               val intent = Intent(context, DetailTeam::class.java)
                intent.putExtra("id", team.id)
                context.startActivity(intent)
            }
            btnUpdate.setOnClickListener{

                val intent = Intent(context, UpdateTeamActivity::class.java)
                intent.putExtra("id", team.id)
                intent.putExtra("name", team.name)
                intent.putExtra("coach", team.coach)
                intent.putExtra("city", team.city)
                intent.putExtra("found", team.found)
                intent.putExtra("image", team.image)


                context.startActivity(intent)
            }
            btnDelete.setOnClickListener{
                AlertDialog.Builder(context).apply {
                    setTitle("Confirmar eliminación")
                    setMessage("¿Estás seguro de que quieres eliminar el equipo ${team.name}?")
                    setPositiveButton("Eliminar") { _, _ ->
                        val viewModel = ViewModelProvider(context as ViewModelStoreOwner).get(
                            TeamViewModel::class.java)
                        viewModel.deleteTeam(team.id).observe(context as LifecycleOwner) { success ->
                            if (success == true) {
                                Toast.makeText(context, "Equipo eliminado correctamente", Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(context, "Error al eliminar el equipo", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                    setNegativeButton("Cancelar", null)
                    show()
                }
            }
        }
        fun ImageView.loadUrl(url: kotlin.String) {
            if (url.isNotEmpty()){
                Picasso.with(context).load(url).into(this)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return teams.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = teams[position]
        holder.bind(item, context)
    }

    fun setData(item: List<Team>) {
        this.teams = item
        notifyDataSetChanged()
    }
}