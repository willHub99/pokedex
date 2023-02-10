package com.whuber.pokedex.utils

class UrlUtils {

    companion object {

        lateinit var previousPage: String
        var currentPage: String = "https://pokeapi.co/api/v2/"
        lateinit var nextPage: String

        fun previousPage(url: String) {
            previousPage = url
        }

        fun nextPage(url: String) {
            nextPage = url
        }


    }


}