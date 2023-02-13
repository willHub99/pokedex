package com.whuber.pokedex.viewmodel

import android.app.Application
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.whuber.pokedex.R
import com.whuber.pokedex.api.ListPokemonResult
import com.whuber.pokedex.constants.PokemonApiConstants
import com.whuber.pokedex.core.State
import com.whuber.pokedex.databinding.ActivityMainBinding
import com.whuber.pokedex.model.PokemonModel
import com.whuber.pokedex.recyclerview.adapater.PokemonAdapter
import com.whuber.pokedex.repository.PokemonRepository
import com.whuber.pokedex.utils.PaginationUtils
import com.whuber.pokedex.utils.UrlUtils
import kotlinx.coroutines.launch

class MainViewModel(private val repository: PokemonRepository, private val pagination: PaginationUtils): ViewModel() {

    private var _listPokemonResult: MutableLiveData<State<List<ListPokemonResult>>> = MutableLiveData()
    var listPokemonResult: LiveData<State<List<ListPokemonResult>>> = _listPokemonResult

    fun getPagePokemon() {
        getSafePagePokemon()
    }

    private fun getSafePagePokemon() = viewModelScope.launch {
        try {
            _listPokemonResult.value = State.Loading
            val response = repository.getPagePokemon()

            response.next?.let { UrlUtils.nextPage(it) }
            response.previous?.let { UrlUtils.previousPage(it) }

            getSafePokemon(response.results)
        } catch (t: Throwable) {
            _listPokemonResult.postValue(State.Error(t))
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
            _listPokemonResult.value = State.Success(pokemonListResult)
        } catch (e: Throwable) {
            _listPokemonResult.value = State.Error(e)
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
            _listPokemonResult.value = State.Loading
            val response = repository.getPagePagination(offset, limit)

            response.next?.let { UrlUtils.nextPage(it) }
            response.previous?.let { UrlUtils.previousPage(it) }

            getSafePokemon(response.results)
        } catch (t: Throwable) {
            _listPokemonResult.value = State.Error(t)
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