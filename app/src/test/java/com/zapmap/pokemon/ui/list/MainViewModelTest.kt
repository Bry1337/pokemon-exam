package com.zapmap.pokemon.ui.list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.zapmap.pokemon.model.PokemonItem
import com.zapmap.pokemon.repository.IPokemonRepository
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotSame
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
internal class MainViewModelTest {

    // Required to run LiveData on the main thread in tests
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    val pokemonRepository: IPokemonRepository = Mockito.mock(IPokemonRepository::class.java)

    private val listOfPokemon = arrayListOf(
        PokemonItem("Charmander", ""),
        PokemonItem("Squirtle", ""),
        PokemonItem("Bulbasaur", "")
    )

    @Test
    fun testFetchPokemon_returnList() {
        val viewModel = MainViewModel(pokemonRepository)
        runBlocking {
            Mockito.`when`(pokemonRepository.getPokemons(0, 50)).thenReturn(
                arrayListOf(
                    PokemonItem("Charmander", ""),
                    PokemonItem("Squirtle", ""),
                    PokemonItem("Bulbasaur", "")
                )
            )
            viewModel.fetchPokemon()
        }
        assertEquals(listOfPokemon, viewModel.list.value)
    }

    @Test
    fun testFetchPokemon_returnEmptyOrNull() {
        val viewModel = MainViewModel(pokemonRepository)
        runBlocking {
            Mockito.`when`(pokemonRepository.getPokemons(0, 50)).thenReturn(
                arrayListOf<PokemonItem>()
            )
            viewModel.fetchPokemon()
        }
        assertEquals(null, viewModel.list.value)
    }


    @Test
    fun testFetchPokemon_returnNotSame() {
        val viewModel = MainViewModel(pokemonRepository)
        runBlocking {
            Mockito.`when`(pokemonRepository.getPokemons(0, 50)).thenReturn(
                arrayListOf<PokemonItem>()
            )
            viewModel.fetchPokemon()
        }
        assertNotSame(arrayListOf<PokemonItem>(), viewModel.list.value)
    }
}