package com.whuber.pokedex.utils

class UrlUtils {

    companion object {

        lateinit var previousPage: String
        lateinit var nextPage: String

        const val urlPartOffset: String = "offset="
        const val urlPartLimit: String = "limit="

        fun previousPage(url: String) {
            previousPage = url ?: ""
        }

        fun nextPage(url: String) {
            nextPage = url ?: ""
        }


    }


}