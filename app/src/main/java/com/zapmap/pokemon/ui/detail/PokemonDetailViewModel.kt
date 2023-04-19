package com.zapmap.pokemon.ui.detail

import androidx.lifecycle.ViewModel
import com.zapmap.pokemon.model.PokemonDetail
import com.zapmap.pokemon.repository.IPokemonRepository

class PokemonDetailViewModel(private val repository: IPokemonRepository): ViewModel() {

    suspend fun getDetailById(id: Int): PokemonDetail? {
        return repository.getPokemonDetailById(id)
    }
}
