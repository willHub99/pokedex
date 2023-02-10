package com.whuber.pokedex.api

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonService {

    @GET("pokemon")
    suspend fun getPagePokemon(): PokemonModelResponse

    @GET("pokemon/{id}")
    suspend fun getPokemonById(@Path("id") id: Int): ListPokemonResult

    @GET("pokemon")
    suspend fun getPagePagination(@Query("offset") offset: Int, @Query("limit") limit: Int): PokemonModelResponse


}