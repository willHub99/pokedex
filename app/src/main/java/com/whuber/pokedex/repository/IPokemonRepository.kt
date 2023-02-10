package com.whuber.pokedex.repository

import com.whuber.pokedex.api.ListPokemonResult
import com.whuber.pokedex.api.PokemonModelResponse

interface IPokemonRepository {
    suspend fun getPagePokemon(): PokemonModelResponse
    suspend fun getPokemonById(id: Int): ListPokemonResult
    suspend fun getPagePagination(offset: Int, limit: Int): PokemonModelResponse
}