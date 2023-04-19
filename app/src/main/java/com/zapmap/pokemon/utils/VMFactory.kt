package com.zapmap.pokemon.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zapmap.pokemon.api.Api
import com.zapmap.pokemon.repository.PokemonRepository
import com.zapmap.pokemon.ui.list.MainViewModel
import com.zapmap.pokemon.ui.detail.PokemonDetailViewModel


object VMFactory: ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val repository = PokemonRepository(Api.getApi())
        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> MainViewModel(repository) as T
            modelClass.isAssignableFrom(PokemonDetailViewModel::class.java) -> PokemonDetailViewModel(repository) as T
            else -> throw Exception("View Model class not found")
        }
    }
}
