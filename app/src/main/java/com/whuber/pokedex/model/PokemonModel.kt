package com.whuber.pokedex.model

import com.whuber.pokedex.api.ListStates

data class PokemonModel(
    val id: Int,
    val name: String,
    val height: Int,
    val weight: Int,
    val types: MutableList<String>,
    val stats: List<ListStates> = mutableListOf<ListStates>()
) {

    val url = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/home/$id.png"

}