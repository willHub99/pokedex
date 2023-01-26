package com.whuber.pokedex.utils

import android.graphics.pdf.PdfDocument.Page

class UrlUtils {

    companion object {

        lateinit var previousPage: String
        var currentPage: String = "https://pokeapi.co/api/v2/"
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