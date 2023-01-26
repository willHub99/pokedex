package com.whuber.pokedex.utils

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

internal class PaginationUtilsTest {

    private val BASE_URL: String = "https://pokeapi.co/api/v2/pokemon"
    private val BASE_OFFSET_URL_BASE = "offset="
    private val BASE_LIMIT_URL_BASE = "limit="
    private val pagination: PaginationUtils = PaginationUtils()

    @ParameterizedTest
    @ValueSource(
        strings =
        [
            "https://pokeapi.co/api/v2/pokemon?offset=20&limit=20",
            "https://pokeapi.co/api/v2/pokemon?offset=40&limit=20",
            "https://pokeapi.co/api/v2/pokemon?offset=60&limit=20",
            "https://pokeapi.co/api/v2/pokemon?&limit=20",
            "https://pokeapi.co/api/v2/pokemon"
        ]
    )
    fun getOffset(url: String) {
        val offsetSplit: List<String> = url.replace("?", "").replace(BASE_URL, "").replace(BASE_OFFSET_URL_BASE, "").split("&")
        val offset: Int = if (offsetSplit[0].isNullOrBlank()) 0 else offsetSplit[0].toInt()
        assertEquals(offset, pagination.getOffset(url))
    }

    @ParameterizedTest
    @ValueSource(
        strings =
        [
            "https://pokeapi.co/api/v2/pokemon?offset=20&limit=20",
            "https://pokeapi.co/api/v2/pokemon?offset=40&limit=20",
            "https://pokeapi.co/api/v2/pokemon?offset=60&limit=20",
            "https://pokeapi.co/api/v2/pokemon?offset=120&",
            "https://pokeapi.co/api/v2/pokemon"
        ]
    )
    fun getLimit(url: String) {
        val offsetSplit: List<String> = url.replace("?", "").replace(BASE_URL, "").replace(BASE_OFFSET_URL_BASE, "").split("&")
        val offset: Int = if (offsetSplit[0].isNullOrBlank()) 0 else offsetSplit[0].toInt()
        assertEquals(offset, pagination.getOffset(url))
    }
}