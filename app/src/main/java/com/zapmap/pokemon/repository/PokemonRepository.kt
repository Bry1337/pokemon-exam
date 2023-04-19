package com.zapmap.pokemon.repository

import com.zapmap.pokemon.api.Api
import com.zapmap.pokemon.model.PokemonDetail
import com.zapmap.pokemon.model.PokemonItem
import com.zapmap.pokemon.model.PokemonType

class PokemonRepository(private val pokemonApi: Api.PokemonApi) : IPokemonRepository {
    override suspend fun getPokemons(page: Int, limit: Int): List<PokemonItem> {
        val response = pokemonApi.fetchPokemons(limit = limit, offset = page)
        if (response.isSuccessful) {
            return response.body()?.pokemonItems?.map {
                PokemonItem(it.name, it.url)
            }?.toList() ?: emptyList()
        }

        return emptyList()
    }

    override suspend fun getPokemonDetailById(id: Int): PokemonDetail? {
        val response = pokemonApi.fetchPokemonById(id = id)
        if (response.isSuccessful) {
            response.body()?.let { pokemon ->
                return PokemonDetail(
                    name = pokemon.name,
                    height = pokemon.height,
                    weight = pokemon.weight,
                    frontDefault = pokemon.sprites.frontDefault,
                    types = pokemon.types.map { item ->
                        PokemonType(item.type.name, item.type.url)
                    }
                )
            }
        }

        return null
    }
}
