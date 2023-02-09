package com.whuber.pokedex.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.RecyclerView
import com.whuber.pokedex.api.ListPokemonResult
import com.whuber.pokedex.api.PokemonModelResponse
import com.whuber.pokedex.constants.PokemonApiConstants
import com.whuber.pokedex.model.PokemonModel
import com.whuber.pokedex.recyclerview.adapater.PokemonAdapter
import com.whuber.pokedex.repository.PokemonRepository
import com.whuber.pokedex.utils.PaginationUtils
import com.whuber.pokedex.utils.UrlUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val BASE_URL: String = "https://pokeapi.co/api/v2/pokemon/"

class MainViewModel: ViewModel() {

    private var _listPokemonResult: MutableLiveData<List<ListPokemonResult>> = MutableLiveData()
    var listPokemonResult: LiveData<List<ListPokemonResult>> = _listPokemonResult


    private val repository = PokemonRepository()
    private var pagination = PaginationUtils()

    fun getPagePokemon() {
        getSafePagePokemon()
    }

    private fun getSafePagePokemon() = viewModelScope.launch {
        try {
            val response = repository.getPagePokemon()

            response.next?.let { UrlUtils.nextPage(it) }
            response.previous?.let { UrlUtils.previousPage(it) }

            response.results.let { getSafePokemon(it) }
        } catch (t: Throwable) {
            Log.d("getSafePagePokemon", t.toString())
        }
    }

    private fun getSafePokemon(listPokemonResult: List<ListPokemonResult>) = viewModelScope.launch {
        try {
            val pokemonListResult: MutableList<ListPokemonResult> = mutableListOf<ListPokemonResult>()
            for (pokemon in listPokemonResult) run {
                val id: Int = getIdFromUrl(pokemon.url)
                repository.getPokemonById(id).let {
                    pokemonListResult.add(it)
                }
            }
            _listPokemonResult.value = pokemonListResult
        } catch (e: Throwable) {
            Log.d("getSafePokemon", e.toString())
        }
    }

    fun convertListPokemonResultToListPokemonModel(listPokemonResult: List<ListPokemonResult>): List<PokemonModel> {
        val pokemonListModel: MutableList<PokemonModel> = mutableListOf<PokemonModel>()
        for (pokemonResult in listPokemonResult) {
            pokemonResult.apply {
                val pokemon = PokemonModel(
                    id,
                    name,
                    height,
                    weight,
                    types.map {type ->
                        type.type.type
                    } as MutableList<String>,
                    stats
                )
                pokemonListModel.add(pokemon)
            }
        }
        return pokemonListModel
    }

    private fun getIdFromUrl(url: String): Int {
        return url.replace(PokemonApiConstants.BASE_URL_POKEMON, "").replace("/", "").trim().toInt()
    }


    fun getPageFromPagination(url: String) {
        val offset: Int = pagination.getOffset(url)
        val limit: Int =  pagination.getLimit(url)
        getSafePageFromPagination(offset, limit)
    }

    private fun getSafePageFromPagination(offset: Int, limit: Int) = viewModelScope.launch {
        try {
            val response = repository.getPagePagination(offset, limit)

            response.next?.let { UrlUtils.nextPage(it) }
            response.previous?.let { UrlUtils.previousPage(it) }

            response.results.let { getSafePokemon(it) }
        } catch (t: Throwable) {
            Log.d("getSafePageFromPagination", t.toString())
        }
    }

    fun filter(text: String, pokemonList: List<PokemonModel>, adapter: PokemonAdapter) {
        val filterPokemonList: MutableList<PokemonModel> = pokemonList.filter {
            it.name.contains(text)
        } as MutableList<PokemonModel>
        adapter.filter(filterPokemonList)
    }

}