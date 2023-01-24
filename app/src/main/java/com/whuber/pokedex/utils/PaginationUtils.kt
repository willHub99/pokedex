package com.whuber.pokedex.utils

class PaginationUtils {

    //"https://pokeapi.co/api/v2/pokemon?offset=20&limit=20"
    private val baseUrl: String = "https://pokeapi.co/api/v2/pokemon?"

    fun getOffset(url: String): Int {
        return url.replace(baseUrl, "").split("&")[0].replace("offset=", "").trim().toInt()
    }

    fun getLimit(url: String): Int {
        return url.replace(baseUrl, "").split("&")[1].replace("limit=", "").trim().toInt()
    }

}