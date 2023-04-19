package com.zapmap.pokemon.ui.list

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.zapmap.pokemon.ZoogleAnalytics
import com.zapmap.pokemon.ZoogleAnalyticsEvent
import com.zapmap.pokemon.databinding.ActivityMainBinding
import com.zapmap.pokemon.model.PokemonItem
import com.zapmap.pokemon.ui.detail.PokemonDetailActivity
import com.zapmap.pokemon.utils.VMFactory
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val pokemonAdapter = PokemonAdapter { pokemonItem -> onPokemonClicked(pokemonItem) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val viewModel: MainViewModel by viewModels { VMFactory }

        binding.recyclerViewPokemon.adapter = pokemonAdapter
        binding.recyclerViewPokemon.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (!recyclerView.canScrollVertically(1)) {
                    lifecycleScope.launch {
                        viewModel.fetchPokemon()
                    }
                }
            }
        })

        viewModel.list.observe(this) { pokemonAdapter.updateItems(it) }

        lifecycleScope.launchWhenCreated { viewModel.fetchPokemon() }
    }

    private fun onPokemonClicked(pokemonItem: PokemonItem) {
        ZoogleAnalytics.logEvent(
            ZoogleAnalyticsEvent(
                "pokemon_selected",
                mapOf("id" to pokemonItem.getId().toString())
            )
        )

        Intent(this@MainActivity, PokemonDetailActivity::class.java).apply {
            putExtra(PokemonDetailActivity.KEY_POKEMON, pokemonItem)
            startActivity(this@apply)
        }
    }
}
