package com.zapmap.pokemon.ui.detail

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import coil.load
import com.zapmap.pokemon.databinding.ActivityPokemonDetailBinding
import com.zapmap.pokemon.model.PokemonDetail
import com.zapmap.pokemon.model.PokemonItem
import com.zapmap.pokemon.utils.VMFactory

class PokemonDetailActivity : AppCompatActivity() {
    companion object {
        const val KEY_POKEMON = "pokemon_item"
    }

    private lateinit var binding: ActivityPokemonDetailBinding

    private val typesAdapter = TypesAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPokemonDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.setNavigationOnClickListener { onBackPressed() }

        val viewModel: PokemonDetailViewModel by viewModels { VMFactory }

        intent.getParcelableExtra<PokemonItem?>(KEY_POKEMON)?.run {
            lifecycleScope.launchWhenCreated {
                val id = getId()
                viewModel.getDetailById(id)?.let { pokemonDetail ->
                    displayPokemon(pokemonDetail)
                }
            }
            binding.recyclerViewTypes.adapter = typesAdapter
            binding.textViewPokemonName.text = name
        }
    }

    private fun displayPokemon(pokemon: PokemonDetail) {
        binding.apply {
            textViewPokemonName.text = pokemon.name.capitalize()
            textViewWeight.text = pokemon.weight.toString()
            textViewHeight.text = pokemon.height.toString()
            imageViewPokemonFront.load(pokemon.frontDefault)
            typesAdapter.updateItems(pokemon.types.map { it.name })
        }
    }
}
