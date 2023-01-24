package com.whuber.pokedex.repository

import com.whuber.pokedex.api.ListPokemonResult
import com.whuber.pokedex.api.PokemonModelResponse
import com.whuber.pokedex.api.PokemonService
import com.whuber.pokedex.model.PokemonModel
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Path


private const val BASE_URL = "https://pokeapi.co/api/v2/"

class PokemonRepository {

        private var retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        private val service: PokemonService = retrofit.create(PokemonService::class.java)

        fun getPagePokemon(): PokemonModelResponse? {
            val call: Call<PokemonModelResponse> = service.listPokemons()
            return call.execute().body()
        }

        fun getPagePaginationPokemon(offset: Int = 0, limit: Int = 0): PokemonModelResponse? {
            val call: Call<PokemonModelResponse> = service.getPokemonPagination(offset, limit)
            return call.execute().body()
        }

        fun getPokemon(id: Int): ListPokemonResult? {
            val call = service.getPokemon(id)
            return call.execute().body()
        }

}