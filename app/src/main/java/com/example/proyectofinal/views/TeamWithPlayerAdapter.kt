package com.example.proyectofinal.views

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
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
import com.example.proyectofinal.repository.room.entity.Player
import com.example.proyectofinal.repository.room.entity.TeamWithPlayers
import com.example.proyectofinal.viewmodels.PlayerViewModel
import com.example.proyectofinal.viewmodels.TeamViewModel
import com.squareup.picasso.Picasso

class PlayerAdapter: RecyclerView.Adapter<PlayerAdapter.ViewHolder>() {

    private var players = emptyList<Player>()
    private lateinit var context: Context

    fun PlayerAdapter(context: Context){
        this.context = context
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name = view.findViewById(R.id.tvOne) as TextView
        val age = view.findViewById(R.id.tvTwo) as TextView
        val position = view.findViewById(R.id.tvThree) as TextView
        val photo = view.findViewById(R.id.ivPhoto) as ImageView
        val btnUpdate = view.findViewById(R.id.btnUpdate) as ImageView
        val btnDelete= view.findViewById(R.id.btnDelete) as ImageView



        fun bind(player: Player, context: Context){
            name.text = player.name
            age.text = player.age.toString()
            position.text = player.position
            if(player.image != null){
                photo.loadUrl(player.image!!)
            }
            btnUpdate.setOnClickListener {
                val intent = Intent(context, UpdatePlayerActivity::class.java)
                intent.putExtra("id", player.id)
                intent.putExtra("name", player.name)
                intent.putExtra("age", player.age.toString())
                intent.putExtra("position", player.position)
                intent.putExtra("number", player.number)
                intent.putExtra("image", player.image)
                context.startActivity(intent)
            }
            btnDelete.setOnClickListener{
                AlertDialog.Builder(context).apply {
                    setTitle("Confirmar eliminación")
                    setMessage("¿Estás seguro de que quieres eliminar el jugador ${player.name}?")
                    setPositiveButton("Eliminar") { _, _ ->
                        val viewModel = ViewModelProvider(context as ViewModelStoreOwner).get(
                            PlayerViewModel::class.java)
                        viewModel.deletePlayer(player.id).observe(context as LifecycleOwner) { success ->
                            if (success == true) {
                                Toast.makeText(context, "Jugador eliminado correctamente", Toast.LENGTH_SHORT).show()
                                val intent = Intent(context, MainActivity::class.java)
                                context.startActivity(intent)

                            } else {
                                Toast.makeText(context, "Error al eliminar el jugador", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                    setNegativeButton("Cancelar", null)
                    show()
                }
            }



        }
        fun ImageView.loadUrl(url: String) {
            if (url.isNotEmpty()){
                Picasso.with(context).load(url).into(this)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerAdapter.ViewHolder {

        return PlayerAdapter.ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return players.size
    }

    override fun onBindViewHolder(holder: PlayerAdapter.ViewHolder, position: Int) {
        val item = players[position]
        holder.bind(item, context)
    }

    fun setData(players: List<Player>) {
        this.players = players
        notifyDataSetChanged()
    }

}