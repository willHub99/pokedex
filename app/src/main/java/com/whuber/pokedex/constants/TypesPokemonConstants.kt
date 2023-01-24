package com.whuber.pokedex.constants

import android.content.Context
import androidx.core.content.ContextCompat
import com.whuber.pokedex.R

class TypesPokemonConstants(context :Context) {

    private val DEFAULT_COLOR: Int = ContextCompat.getColor(context, R.color.primary_normal)

    private val colorsByTypesPokemon = mapOf<String, Int>(
        "normal" to ContextCompat.getColor(context, R.color.normal_normal),
        "fighting" to ContextCompat.getColor(context, R.color.fighting_normal),
        "flying" to ContextCompat.getColor(context, R.color.flying_normal),
        "poison" to ContextCompat.getColor(context, R.color.poison_normal),
        "ground" to ContextCompat.getColor(context, R.color.ground_normal),
        "rock" to ContextCompat.getColor(context, R.color.rock_normal),
        "bug" to ContextCompat.getColor(context, R.color.bug_normal),
        "ghost" to ContextCompat.getColor(context, R.color.ghost_normal),
        "steel" to ContextCompat.getColor(context, R.color.steel_normal),
        "fire" to ContextCompat.getColor(context, R.color.fire_normal),
        "water" to ContextCompat.getColor(context, R.color.water_normal),
        "grass" to ContextCompat.getColor(context, R.color.grass_normal),
        "electric" to ContextCompat.getColor(context, R.color.eletric_normal),
        "psychic" to ContextCompat.getColor(context, R.color.physic_normal),
        "ice" to ContextCompat.getColor(context, R.color.ice_normal),
        "dragon" to ContextCompat.getColor(context, R.color.dragon_normal),
        "dark" to ContextCompat.getColor(context, R.color.dark_normal),
        "fairy" to ContextCompat.getColor(context, R.color.fairy_normal),
        "unknown" to ContextCompat.getColor(context, R.color.primary_normal),
        "shadow" to ContextCompat.getColor(context, R.color.ghost_normal))

    private val colorsTrackColorByTypePokemon = mapOf<String, Int>(
        "normal" to ContextCompat.getColor(context, R.color.normal_dark),
        "fighting" to ContextCompat.getColor(context, R.color.fighting_dark),
        "flying" to ContextCompat.getColor(context, R.color.flying_dark),
        "poison" to ContextCompat.getColor(context, R.color.poison_dark),
        "ground" to ContextCompat.getColor(context, R.color.ground_dark),
        "rock" to ContextCompat.getColor(context, R.color.rock_dark),
        "bug" to ContextCompat.getColor(context, R.color.bug_dark),
        "ghost" to ContextCompat.getColor(context, R.color.ghost_dark),
        "steel" to ContextCompat.getColor(context, R.color.steel_dark),
        "fire" to ContextCompat.getColor(context, R.color.fire_dark),
        "water" to ContextCompat.getColor(context, R.color.water_dark),
        "grass" to ContextCompat.getColor(context, R.color.grass_dark),
        "electric" to ContextCompat.getColor(context, R.color.eletric_dark),
        "psychic" to ContextCompat.getColor(context, R.color.physic_dark),
        "ice" to ContextCompat.getColor(context, R.color.ice_dark),
        "dragon" to ContextCompat.getColor(context, R.color.dragon_dark),
        "dark" to ContextCompat.getColor(context, R.color.dark_dark),
        "fairy" to ContextCompat.getColor(context, R.color.fairy_dark),
        "unknown" to ContextCompat.getColor(context, R.color.primary_dark),
        "shadow" to ContextCompat.getColor(context, R.color.ghost_dark))


    fun getColorByTypePokemon(type: String): Int {
        return if (colorsByTypesPokemon.containsKey(type)) {
            colorsByTypesPokemon.getValue(type)
        } else {
            DEFAULT_COLOR
        }
    }

    fun getTrackColorByTypePokemon(type: String): Int {
        return if (colorsTrackColorByTypePokemon.containsKey(type)) {
            colorsTrackColorByTypePokemon.getValue(type)
        } else {
            DEFAULT_COLOR
        }
    }

}