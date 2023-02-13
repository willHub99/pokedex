package com.whuber.pokedex.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.whuber.pokedex.api.ListPokemonResult
import com.whuber.pokedex.core.State
import com.whuber.pokedex.repository.PokemonRepository
import kotlinx.coroutines.launch

class PokemonDetailViewModel(private val repository: PokemonRepository): ViewModel() {

    private val _pokemonResponse: MutableLiveData<State<ListPokemonResult>> = MutableLiveData<State<ListPokemonResult>>()
    val pokemonResponse: LiveData<State<ListPokemonResult>> = _pokemonResponse

    fun getPokemon(id: Int) = viewModelScope.launch {
        try {
            _pokemonResponse.value = State.Loading
            _pokemonResponse.value = State.Success(repository.getPokemonById(id))
        } catch (e: Throwable) {
            _pokemonResponse.value = State.Error(e)
            Log.d("getPokemon", e.toString())
        }

    }

}