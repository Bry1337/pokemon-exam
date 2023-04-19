package com.zapmap.pokemon.repository

import com.zapmap.pokemon.api.Api
import com.zapmap.pokemon.api.PokemonsResponse
import com.zapmap.pokemon.api.RemotePokemon
import com.zapmap.pokemon.api.RemotePokemonItem
import com.zapmap.pokemon.api.Sprite
import com.zapmap.pokemon.api.Type
import com.zapmap.pokemon.api.TypeItem
import com.zapmap.pokemon.model.PokemonDetail
import com.zapmap.pokemon.model.PokemonItem
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertNull
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import retrofit2.Response

internal class PokemonRepositoryTest {

    @Mock
    val pokemonApi: Api.PokemonApi = Mockito.mock(Api.PokemonApi::class.java)

    lateinit var repository: PokemonRepository

    @Before
    fun setup() {
        repository = PokemonRepository(pokemonApi)
    }

    @Test
    fun getPokemons_returnObject() {
        var pokemonItem: List<PokemonItem>
        val pokemonResponse = PokemonsResponse(
            1, "Pikachu", arrayListOf(
                RemotePokemonItem("Pikachu", "")
            )
        )
        val remotePokemonsResponse: Response<PokemonsResponse> =
            Response.success(200, pokemonResponse)
        runBlocking {
            Mockito.`when`(pokemonApi.fetchPokemons(0, 1)).thenReturn(remotePokemonsResponse)
            pokemonItem = repository.getPokemons(1, 0)
        }
        println(pokemonItem)
        assertNotNull(pokemonItem)
    }

    @Test
    fun getPokemons_returnEmpty() {
        var pokemonItem: List<PokemonItem>
        val remotePokemonsResponse: Response<PokemonsResponse> =
            Response.error(400, "{'message':'error messsage".toResponseBody())
        runBlocking {
            Mockito.`when`(pokemonApi.fetchPokemons(0, 1)).thenReturn(remotePokemonsResponse)
            pokemonItem = repository.getPokemons(1, 0)
        }
        println(pokemonItem)
        assertEquals(arrayListOf<PokemonItem>(), pokemonItem)
    }

    @Test
    fun getPokemonDetailById_returnNotNull() {
        val remotePokemon: RemotePokemon = RemotePokemon(
            "Pikachu", 20, 90, arrayListOf<TypeItem>(
                TypeItem(
                    Type("Lightning", "")
                )
            ), Sprite("default")
        )
        val pokemonDetailResponse: Response<RemotePokemon> = Response.success(200, remotePokemon)
        var pokemonDetail: PokemonDetail?
        runBlocking {
            Mockito.`when`(pokemonApi.fetchPokemonById(1)).thenReturn(pokemonDetailResponse)
            pokemonDetail = repository.getPokemonDetailById(1)
        }
        assertNotNull(pokemonDetail)
    }

    @Test
    fun getPokemonDetailById_returnNull() {
        val remotePokemon: RemotePokemon? = null
        val pokemonDetailResponse: Response<RemotePokemon> = Response.success(200, remotePokemon)
        var pokemonDetail: PokemonDetail?
        runBlocking {
            Mockito.`when`(pokemonApi.fetchPokemonById(1)).thenReturn(pokemonDetailResponse)
            pokemonDetail = repository.getPokemonDetailById(1)
        }
        assertNull(pokemonDetail)


    }
}