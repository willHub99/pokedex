package com.whuber.pokedex.core

import com.whuber.pokedex.api.CallApiPokemon
import com.whuber.pokedex.api.PokemonService
import com.whuber.pokedex.constants.PokemonApiConstants
import com.whuber.pokedex.repository.IPokemonRepository
import com.whuber.pokedex.repository.PokemonRepository
import com.whuber.pokedex.utils.PaginationUtils
import com.whuber.pokedex.viewmodel.MainViewModel
import com.whuber.pokedex.viewmodel.PokemonDetailViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

var appModuleMainActivity = module {
    single<IPokemonRepository> {
        PokemonRepository(get())
    }
    single {
        PaginationUtils()
    }
    viewModel {
        MainViewModel(get(), get())
    }
}

var appModulePokemonActivity = module {
    viewModel {
        PokemonDetailViewModel(get())
    }
}

var appModulePokemonRepository = module {
    single {
        CallApiPokemon(get())
    }
    single {
        PokemonRepository(get())
    }
}

fun createRetrofit(): Retrofit {
    return Retrofit.Builder()
        .baseUrl(PokemonApiConstants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

var appModuleCreateRetrofit = module {

    single {
        val retrofit: Retrofit = createRetrofit()
        retrofit.create(PokemonService::class.java)
    }

    single {
        CallApiPokemon(get())
    }
}





