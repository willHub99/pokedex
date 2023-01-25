package com.whuber.pokedex.viewmodel

import androidx.annotation.UiThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.whuber.pokedex.api.ListPokemonResult
import com.whuber.pokedex.api.PokemonModelResponse
import com.whuber.pokedex.model.PokemonModel
import com.whuber.pokedex.recyclerview.adapater.PokemonAdapter
import com.whuber.pokedex.repository.PokemonRepository
import com.whuber.pokedex.utils.PaginationUtils
import com.whuber.pokedex.utils.UrlUtils

private const val BASE_URL: String = "https://pokeapi.co/api/v2/pokemon/"

class MainViewModel: ViewModel() {

    var listPokemonData: MutableLiveData<List<PokemonModel>> = MutableLiveData()
    var list: LiveData<List<PokemonModel>> = listPokemonData

    private val repository = PokemonRepository()
    private var pagination = PaginationUtils()

    fun getPagePokemon (): PokemonModelResponse = repository.getPagePokemon()!!

    fun pagination(url: String): PokemonModelResponse {

        var offset: Int = 0
        var limit: Int = 0
        if (url.contains(UrlUtils.urlPartOffset)) {
            offset = pagination.getOffset(url)
        }
        if (url.contains(UrlUtils.urlPartLimit)) {
            limit = pagination.getLimit(url)
        }
        return repository.getPagePaginationPokemon(offset, limit)!!
    }

    fun getPokemons(listPokemonsResponse: List<ListPokemonResult>): List<PokemonModel> {
        return listPokemonsResponse.map { pokemonResult ->
            val id = pokemonResult.url.replace(BASE_URL, "").replace("/","").toInt()
            val pokemonResult = repository.getPokemon(id)
            pokemonResult?.let { getPokemon(it.id) }!!
        }
    }

    fun getPokemon (id: Int): PokemonModel {
        val pokemonResult = repository.getPokemon(id)
        return pokemonResult?.let {pokemon ->
            PokemonModel(
                pokemon.id,
                pokemon.name,
                pokemon.height,
                pokemon.weight,
                pokemon.types.map {type ->
                    type.type.type
                } as MutableList<String>,
                pokemon.stats
            )
        }!!
    }

    fun filter(text: String, pokemons: List<PokemonModel>, adapter: PokemonAdapter) {
        var filterPokemons: MutableList<PokemonModel> = pokemons.filter {
            it.name.contains(text)
        } as MutableList<PokemonModel>
        adapter.filter(filterPokemons)
    }

    fun getPokemonsPagination(adapter: PokemonAdapter, recyclerView: RecyclerView, url: String) {
            Thread(Runnable {

                var pagination: PokemonModelResponse = pagination(url)

                var listPokemons: List<PokemonModel> = ArrayList<PokemonModel>()

                pagination.let {
                    UrlUtils.previousPage(it.previous.toString())
                    UrlUtils.nextPage(it.next.toString())
                }
                pagination.results.let {
                    listPokemons = getPokemons(it)
                    recyclerView.post {
                        listPokemonData.value = listPokemons
                        adapter.pagination(listPokemons)
                    }
                }
            }).start()
    }

}