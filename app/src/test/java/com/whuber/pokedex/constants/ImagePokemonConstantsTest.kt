package com.whuber.pokedex.constants

import org.junit.jupiter.api.Assertions.*

import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

internal class ImagePokemonConstantsTest {

    private val imagePokemonConstants: ImagePokemonConstants = ImagePokemonConstants()
    @ParameterizedTest
    @CsvSource(
        "normal,         2131165400",
        "flying,        2131165321",
        "poison, 2131165414",
        "grass,    2131165323",
        "null, 2131165400",
        "water, 2131165427"
    )
    fun getImageByTypePokemon(type: String, drawable: String) {
        val resourceInt: Int = drawable.toInt()
        assertEquals(resourceInt, imagePokemonConstants.getImageByTypePokemon(type))
    }
}

