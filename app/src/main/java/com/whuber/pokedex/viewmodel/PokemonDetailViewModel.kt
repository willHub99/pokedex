package com.whuber.pokedex.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.whuber.pokedex.api.ListPokemonResult
import com.whuber.pokedex.model.PokemonModel
import com.whuber.pokedex.repository.PokemonRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PokemonDetailViewModel: ViewModel() {

    private val repository = PokemonRepository()
    private val _pokemonResponse: MutableLiveData<ListPokemonResult> = MutableLiveData<ListPokemonResult>()
    val pokemonResponse: LiveData<ListPokemonResult> = _pokemonResponse

    fun getPokemon(id: Int) = viewModelScope.launch {
        try {
            _pokemonResponse.value = repository.getPokemonById(id)
        } catch (e: Throwable) {
            Log.d("getPokemon", e.toString())
        }

    }

}