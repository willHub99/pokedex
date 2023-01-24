package com.whuber.pokedex.viewmodel

import com.whuber.pokedex.model.PokemonModel
import com.whuber.pokedex.repository.PokemonRepository

class PokemonDetailViewModel {

    private val repository = PokemonRepository()

    fun getPokemon(id: Int): PokemonModel  {
        val pokemonResult = repository.getPokemon(id)
        return pokemonResult?.let {pokemon ->
            PokemonModel(
                pokemon.id,
                pokemon.name,
                pokemon.height,
                pokemon.weight,
                pokemon.types.map {type ->
                    type.type.type
                } as MutableList<String>,
                pokemon.stats
            )
        }!!
    }

}