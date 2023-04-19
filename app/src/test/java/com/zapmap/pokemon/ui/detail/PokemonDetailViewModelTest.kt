package com.zapmap.pokemon.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.zapmap.pokemon.model.PokemonDetail
import com.zapmap.pokemon.model.PokemonType
import com.zapmap.pokemon.repository.IPokemonRepository
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
internal class PokemonDetailViewModelTest {

    // Required to run LiveData on the main thread in tests
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    val pokemonRepository: IPokemonRepository = Mockito.mock(IPokemonRepository::class.java)

    lateinit var viewModel: PokemonDetailViewModel

    @Before
    fun setup() {
        viewModel = PokemonDetailViewModel(pokemonRepository)
    }

    @Test
    fun testGetById_returnNotNull() {
        val pikachu = PokemonDetail(
            "Pikachu",
            20,
            90,
            "default",
            arrayListOf(PokemonType("Lightning", ""), PokemonType("Normal", "")))
        lateinit var pokemonDetail: PokemonDetail
        runBlocking {
            Mockito.`when`(pokemonRepository.getPokemonDetailById(1)).thenReturn(
                PokemonDetail(
                    "Pikachu", 20, 90, "default",
                    arrayListOf(PokemonType("Lightning", ""), PokemonType("Normal", ""))
                )
            )
            viewModel.getDetailById(1)?.let {
                pokemonDetail = it
            }
        }
        assertEquals(pikachu, pokemonDetail)
    }

    @Test
    fun testGetById_returnNull() {
        var pokemonDetail: PokemonDetail?
        runBlocking {
            Mockito.`when`(pokemonRepository.getPokemonDetailById(1)).thenReturn(null)
            pokemonDetail = viewModel.getDetailById(1)
        }
        assertNull(pokemonDetail)
    }

}