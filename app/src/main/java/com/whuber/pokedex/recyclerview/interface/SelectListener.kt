package com.whuber.pokedex.recyclerview.`interface`

import com.whuber.pokedex.model.PokemonModel

interface SelectListener {

    fun onItemClick(pokemon: PokemonModel)

}