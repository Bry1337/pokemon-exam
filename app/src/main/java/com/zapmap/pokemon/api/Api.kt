package com.zapmap.pokemon.api

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

object Api {
    fun getApi(): PokemonApi {
        val moshi = Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()

        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder()
            .apply {
                addInterceptor(loggingInterceptor)
            }.build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://pokeapi.co/api/v2/")
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

        return retrofit.create(PokemonApi::class.java)
    }

    interface PokemonApi {
        @GET("pokemon")
        suspend fun fetchPokemons(
            @Query("limit") limit: Int,
            @Query("offset") offset: Int
        ): Response<PokemonsResponse>

        @GET("pokemon/{id}")
        suspend fun fetchPokemonById(
            @Path("id") id: Int,
        ): Response<RemotePokemon>
    }
}