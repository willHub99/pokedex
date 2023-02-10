package com.whuber.pokedex.repository

import com.whuber.pokedex.api.CallApiPokemon
import com.whuber.pokedex.api.ListPokemonResult
import com.whuber.pokedex.api.PokemonModelResponse


class PokemonRepository(private  val api: CallApiPokemon) : IPokemonRepository {

    override suspend fun getPagePokemon(): PokemonModelResponse {
        return api.getPagePokemon()
    }

    override suspend fun getPokemonById(id: Int): ListPokemonResult {
        return api.getPokemonById(id)
    }

    override suspend fun getPagePagination(offset: Int, limit: Int): PokemonModelResponse {
        return api.getPagePagination(offset, limit)
    }

}