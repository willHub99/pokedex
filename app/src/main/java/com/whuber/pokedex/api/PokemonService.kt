package com.whuber.pokedex.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonService {

    @GET("pokemon")
    fun listPokemons(): Call<PokemonModelResponse>

    @GET("pokemon/{id}")
    fun getPokemon(@Path("id") id: Int): Call<ListPokemonResult>

    @GET("pokemon")  //https://pokeapi.co/api/v2/pokemon?offset=20&limit=20
    fun getPokemonPagination(@Query("offset") offset: Int, @Query("limit") limit: Int): Call<PokemonModelResponse>

}