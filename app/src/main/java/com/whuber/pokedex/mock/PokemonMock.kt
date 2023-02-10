package com.whuber.pokedex.mock

import com.whuber.pokedex.model.PokemonModel
import kotlin.random.Random

const val IMAGE_BULBASAUR: String = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/home/1.png"

class PokemonMock {

    private var hp: Int = 0
    private var attack: Int = 0
    private var defense: Int = 0
    private var specialAttack: Int = 0
    private var specialDefense: Int = 0
    private var speed: Int = 0
    private var weight: Int = 0
    private var height: Int = 0
    private var indexRandom: Int = 0
    var listTypes: MutableList<String> = mutableListOf()
    var listPokemons: MutableList<PokemonModel> = mutableListOf()

    fun createPokemon(): List<PokemonModel> {
        for (i in 1..20) {
            generatedRandomAtributes()
            var pokemonName: String = generatedRandomName()
            listTypes = generatedTypesPokemon()
            var pokemon: PokemonModel = PokemonModel(i, pokemonName, height, weight , listTypes , emptyList())
            listPokemons.add(pokemon)
        }
        return listPokemons
    }

    private fun generatedRandomAtributes() {
        hp = Random.nextInt(101)
        attack = Random.nextInt(101)
        defense = Random.nextInt(101)
        specialAttack = Random.nextInt(101)
        specialDefense = Random.nextInt(101)
        speed = Random.nextInt(101)
        weight = Random.nextInt(101)
        height = Random.nextInt(101)
    }

    fun generatedRandomName(): String {
        var listNames = mutableListOf("baseurl", "pikachu", "charmander", "squartle", "charizard", "snorlax", "buterfly", "garrados", "mewtoo", "pidgey", "pidgeot")
        indexRandom = Random.nextInt(listNames.size)
        return listNames[indexRandom]
    }

    fun generatedTypesPokemon(): ArrayList<String> {
        val types= mutableListOf("normal", "fighting", "flying", "poison", "ground", "rock", "bug", "ghost", "steel", "fire", "water", "grass", "electric", "psychic", "ice", "dragon", "dark", "fairy", "unknown", "shadow")
        var list:ArrayList<String> = ArrayList<String>()
        val numberTypes: Int = Random.nextInt(4)

        for (i in 0..numberTypes) {
            indexRandom = Random.nextInt(types.size)
            list.add(types[indexRandom])
        }

        return list
    }


}