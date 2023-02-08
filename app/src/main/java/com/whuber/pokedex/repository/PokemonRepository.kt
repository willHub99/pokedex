package com.whuber.pokedex.repository

import com.whuber.pokedex.api.CallApiPokemon
import com.whuber.pokedex.api.ListPokemonResult
import com.whuber.pokedex.api.PokemonModelResponse
import com.whuber.pokedex.api.PokemonService
import com.whuber.pokedex.model.PokemonModel
import retrofit2.Call
import retrofit2.Response
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

    private val api = CallApiPokemon()

    suspend fun getPagePokemon(): PokemonModelResponse {
        return api.getPagePokemon()
    }

    suspend fun getPokemonById(id: Int): ListPokemonResult {
        return api.getPokemonById(id)
    }

    suspend fun getPagePagination(offset: Int, limit: Int): PokemonModelResponse {
        return api.getPagePagination(offset, limit)
    }


    fun getPokemon(id: Int): ListPokemonResult? {
        val call = service.getPokemon(id)
        return call.execute().body()
    }

}