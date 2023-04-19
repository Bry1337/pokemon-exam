package com.zapmap.pokemon.model

data class PokemonDetail(
    val name: String,
    val weight: Int,
    val height: Int,
    val frontDefault: String,
    val types: List<PokemonType>
)
