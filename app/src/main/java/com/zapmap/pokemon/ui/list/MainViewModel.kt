package com.zapmap.pokemon.ui.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zapmap.pokemon.model.PokemonItem
import com.zapmap.pokemon.repository.IPokemonRepository

class MainViewModel(private val repository: IPokemonRepository): ViewModel() {
    companion object {
        private const val LIMIT = 50
    }

    private var currentOffset = 0

    val list: MutableLiveData<List<PokemonItem>> = MutableLiveData()

    suspend fun fetchPokemon() {
        repository.getPokemons(currentOffset, LIMIT).run {
            if (isNotEmpty()) {
                list.postValue(this)
                currentOffset += LIMIT
            }
        }
    }
}
