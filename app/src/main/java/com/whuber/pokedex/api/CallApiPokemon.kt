package com.whuber.pokedex.api

import com.whuber.pokedex.constants.PokemonApiConstants
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CallApiPokemon {

    private var retrofit = Retrofit.Builder()
        .baseUrl(PokemonApiConstants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val service: PokemonService = retrofit.create(PokemonService::class.java)

    suspend fun getPagePokemon(): PokemonModelResponse {
        return service.getPagePokemon()
    }

    suspend fun getPokemonById(id: Int): ListPokemonResult {
        return service.getPokemonById(id)
    }

    suspend fun getPagePagination(offset: Int, limit: Int): PokemonModelResponse {
        return service.getPagePagination(offset,limit)
    }

}