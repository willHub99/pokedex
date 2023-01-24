package com.whuber.pokedex.api

import com.google.gson.annotations.SerializedName
import com.whuber.pokedex.model.PokemonModel

data class PokemonModelResponse(

    @SerializedName("next")
    val next: String?,

    @SerializedName("previous")
    val previous: String?,

    @SerializedName("results")
    val results: List<ListPokemonResult>)

data class ListPokemonResult(

    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("url")
    val url: String,

    @SerializedName("height")
    val height: Int,

    @SerializedName("weight")
    val weight: Int,

    @SerializedName("types")
    val types: List<PokemonTypes>,

    @SerializedName("stats")
    val stats: List<ListStates>,

)

data class PokemonTypes(

    @SerializedName("slot")
    val slot: Int,

    @SerializedName("type")
    val type: Type

)

data class Type(

    @SerializedName("name")
    val type: String
)

data class ListStates(

    @SerializedName("base_stat")
    val stat: Int,

    @SerializedName("stat")
    val state: State

)

data class State(

    @SerializedName("name")
    val name: String

)

