package com.zapmap.pokemon.repository

import com.zapmap.pokemon.model.PokemonDetail
import com.zapmap.pokemon.model.PokemonItem

interface IPokemonRepository {
    suspend fun getPokemons(page: Int, limit: Int): List<PokemonItem>

    suspend fun getPokemonDetailById(id: Int): PokemonDetail?
}
