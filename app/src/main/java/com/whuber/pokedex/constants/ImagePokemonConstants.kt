package com.whuber.pokedex.constants

import android.content.Context
import com.whuber.pokedex.R

class ImagePokemonConstants(context: Context) {

    private val DEFAULT_ICON_TYPE: Int = R.drawable.normal

    private val iconByTypesPokemon: Map<String, Int> = mapOf(
        "normal" to R.drawable.normal,
        "fighting" to R.drawable.fighting,
        "flying" to R.drawable.flying,
        "poison" to R.drawable.poison,
        "ground" to R.drawable.ground,
        "rock" to R.drawable.rock,
        "bug" to R.drawable.bug,
        "ghost" to R.drawable.ghost,
        "steel" to R.drawable.steel,
        "fire" to R.drawable.fire,
        "water" to R.drawable.water,
        "grass" to R.drawable.grass,
        "electric" to R.drawable.eletric,
        "psychic" to R.drawable.physic,
        "ice" to R.drawable.ice,
        "dragon" to R.drawable.dragon,
        "dark" to R.drawable.dark,
        "fairy" to R.drawable.fairy,
        "unknown" to R.drawable.normal,
        "shadow" to R.drawable.ghost
    )

    fun getImageByTypePokemon(type: String): Int {
        val resource: Int = if (iconByTypesPokemon.containsKey(type)) {
            iconByTypesPokemon.getValue(type)
        } else {
            DEFAULT_ICON_TYPE
        }
        return resource
    }


}