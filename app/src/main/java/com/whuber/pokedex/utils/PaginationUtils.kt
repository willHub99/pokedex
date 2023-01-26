package com.whuber.pokedex.utils

class PaginationUtils {

    //"https://pokeapi.co/api/v2/pokemon?offset=20&limit=20"
    private val baseUrl: String = "https://pokeapi.co/api/v2/pokemon"
    private val baseOffsetUrl: String = "offset="
    private val baseLimitUrl: String = "limit="

    fun getOffset(url: String): Int {
        var offset: Int = 0
        if (url.contains(baseUrl)) {
            val urlWithoutBaseUrl = url.replace("?", "").replace(baseUrl, "")
            if (urlWithoutBaseUrl.contains(baseOffsetUrl)) {
                val urlWithoutOffsetBaseUrl: String = urlWithoutBaseUrl.replace(baseOffsetUrl, "")
                val urlSplited: List<String> = urlWithoutOffsetBaseUrl.split("&")
                offset = if(urlSplited[0].isNullOrEmpty()) 0 else urlSplited[0].toInt()
            }
        }
        return offset
    }

    fun getLimit(url: String): Int {
        var limit: Int = 0
        if (url.contains(baseUrl)) {
            val urlWithoutBaseUrl = url.replace("?", "").replace(baseUrl, "")
            if (urlWithoutBaseUrl.contains(baseLimitUrl)) {
                val urlWithoutLimitBaseUrl: String = urlWithoutBaseUrl.replace(baseLimitUrl, "")
                val urlSplited: List<String> = urlWithoutLimitBaseUrl.split("&")
                limit = if(urlSplited[1].isNullOrEmpty()) 0 else urlSplited[1].toInt()
            }
        }
        return limit
    }

}