package com.zapmap.pokemon.ui.list

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.zapmap.pokemon.R
import com.zapmap.pokemon.model.PokemonItem

class PokemonAdapter(private val callback: (PokemonItem) -> Unit) : RecyclerView.Adapter<PokemonAdapter.ViewHolder>() {

    private val items = mutableListOf<PokemonItem>()

    @SuppressLint("NotifyDataSetChanged")
    fun updateItems(list: List<PokemonItem>) {
        items.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item_pokemon, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        items[position].let { pokemonItem ->
            holder.textViewName.apply {
                text = pokemonItem.name.capitalize()
                setOnClickListener { callback(pokemonItem) }
            }
        }

    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textViewName: TextView = view.findViewById(R.id.textViewName)
    }

    override fun getItemCount(): Int = items.size
}